package com.liberty52.product.service.applicationservice;

import com.liberty52.product.global.adapter.s3.S3UploaderApi;
import com.liberty52.product.global.event.Events;
import com.liberty52.product.global.event.events.OrderRequestDepositEvent;
import com.liberty52.product.global.exception.external.badrequest.RequestForgeryPayException;
import com.liberty52.product.global.exception.external.internalservererror.ConfirmPaymentException;
import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.product.service.controller.dto.*;
import com.liberty52.product.service.entity.*;
import com.liberty52.product.service.entity.payment.Payment;
import com.liberty52.product.service.entity.payment.VBank;
import com.liberty52.product.service.entity.payment.VBankPayment;
import com.liberty52.product.service.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderCreateServiceImpl implements OrderCreateService {

    private static final String RESOURCE_NAME_PRODUCT = "Product";
    private static final String PARAM_NAME_PRODUCT_NAME = "name";
    private static final String RESOURCE_NAME_OPTION_DETAIL = "OptionDetail";
    private static final String PARAM_NAME_OPTION_DETAIL_NAME = "name";
    private final S3UploaderApi s3Uploader;
    private final ProductRepository productRepository;
    private final CustomProductRepository customProductRepository;
    private final OrdersRepository ordersRepository;
    private final OptionDetailRepository optionDetailRepository;
    private final CustomProductOptionRepository customProductOptionRepository;
    private final ConfirmPaymentMapRepository confirmPaymentMapRepository;
    private final VBankRepository vBankRepository;

    @Override
    @Deprecated
    public MonoItemOrderResponseDto save(String authId, MultipartFile imageFile, MonoItemOrderRequestDto dto) {
        // Valid and get resources
        Product product = productRepository.findByName(dto.getProductName())
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME_PRODUCT, PARAM_NAME_PRODUCT_NAME, dto.getProductName()));
        List<OptionDetail> details = new ArrayList<>();
        for (String optionName : dto.getOptions()) {
            OptionDetail optionDetail = optionDetailRepository.findByName(optionName)
                    .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME_OPTION_DETAIL, PARAM_NAME_OPTION_DETAIL_NAME, optionName));
            details.add(optionDetail);
        }

        OrderDestination orderDestination = OrderDestination.create("", "", "", "", "", "");

        // Save Order
        Orders order = ordersRepository.save(Orders.create(authId, dto.getDeliveryPrice(), orderDestination)); // OrderDestination will be saved by cascading

        // Upload Image
        String imgUrl = s3Uploader.upload(imageFile);

        // Save CustomProduct
        CustomProduct customProduct = CustomProduct.create(imgUrl, dto.getQuantity(), authId);
        customProduct.associateWithProduct(product);
        customProduct.associateWithOrder(order);
        customProduct = customProductRepository.save(customProduct);

        // Save CustomProductOption
        for (OptionDetail detail : details) {
            CustomProductOption customProductOption = customProductOptionRepository.save(CustomProductOption.create());
            customProductOption.associate(customProduct);
            customProductOption.associate(detail);
        }

        return MonoItemOrderResponseDto.create(order.getId(), order.getOrderDate(), order.getOrderStatus());
    }

    @Override
    public PreregisterOrderResponseDto preregisterCardPaymentOrders(String authId, PreregisterOrderRequestDto dto, MultipartFile imageFile) {
        Orders order = saveOrder(authId, dto, imageFile);

        Payment<?> payment = Payment.cardOf();
        payment.associate(order);

        return PreregisterOrderResponseDto.of(order.getId(), order.getAmount());
    }

    @Override
    public PaymentConfirmResponseDto confirmFinalApprovalOfCardPayment(String authId, String orderId) {
        AtomicInteger secTimeout = new AtomicInteger(0);

        while (!confirmPaymentMapRepository.containsOrderId(orderId)) {
            try {
                log.info("DELAY WEBHOOK - OrderID: {}, Delay Time: {}", orderId, secTimeout.get());
                Thread.sleep(1000);
                if(secTimeout.incrementAndGet() > 60) {
                    log.error("카드 결제 정보를 확인하는 시간이 초과했습니다. 웹훅 서버를 확인해주세요. OrderId: {}", orderId);
                    throw new ConfirmPaymentException();
                }
            } catch (InterruptedException e) {
                log.error("카드결제 검증요청 스레드에 문제가 발생하였습니다. OrderId: {}", orderId);
                throw new ConfirmPaymentException();
            }
        }

        Orders orders = confirmPaymentMapRepository.getAndRemove(orderId);

        return switch (orders.getPayment().getStatus()) {
            case PAID -> PaymentConfirmResponseDto.of(orderId);
            case FORGERY -> throw new RequestForgeryPayException();
            default -> {
                log.error("주문 결제 상태의 PAID or FORGERY 이외의 상태로 요청되었습니다. 요청주문의 상태: {}", orders.getPayment().getStatus());
                throw new ConfirmPaymentException();
            }
        };
    }

    @Override
    public PaymentVBankResponseDto registerVBankPaymentOrders(String authId, PreregisterOrderRequestDto dto, MultipartFile imageFile) {
        Orders order = saveOrder(authId, dto, imageFile);
        order.changeOrderStatusToWaitingDeposit();

        if (!vBankRepository.existsByAccount(dto.getVbankDto().getVbankInfo())) {
            throw new ResourceNotFoundException("VBANK", "ACCOUNT", dto.getVbankDto().getVbankInfo());
        }

        Payment<?> payment = Payment.vbankOf();
        payment.associate(order);
        payment.setInfo(VBankPayment.VBankPaymentInfo.ofWaitingDeposit(dto.getVbankDto()));

        Events.raise(new OrderRequestDepositEvent(dto.getDestinationDto().getReceiverEmail(), dto.getDestinationDto().getReceiverName(), order));

        return PaymentVBankResponseDto.of(order.getId());
    }

    @Override
    public VBankInfoListResponseDto getVBankInfoList() {
        List<VBank> vbanks = vBankRepository.findAll();
        return VBankInfoListResponseDto.of(
            vbanks.stream().map(vBank -> VBankInfoListResponseDto.VBankInfoDto.of(vBank.getAccount())).toList()
        );
    }

    private Orders saveOrder(String authId, PreregisterOrderRequestDto dto, MultipartFile imageFile) {
        // Valid and get resources
        Product product = productRepository.findByName(dto.getProductDto().getProductName())
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME_PRODUCT, PARAM_NAME_PRODUCT_NAME, dto.getProductDto().getProductName()));

        List<OptionDetail> optionDetails = dto.getProductDto().getOptions().stream()
                .map(optionName -> optionDetailRepository.findByName(optionName)
                        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME_OPTION_DETAIL, PARAM_NAME_OPTION_DETAIL_NAME, optionName)))
                .toList();

        OrderDestination orderDestination = OrderDestination.create(
                dto.getDestinationDto().getReceiverName(),
                dto.getDestinationDto().getReceiverEmail(),
                dto.getDestinationDto().getReceiverPhoneNumber(),
                dto.getDestinationDto().getAddress1(),
                dto.getDestinationDto().getAddress2(),
                dto.getDestinationDto().getZipCode()
        );

        // Save Order
        Orders order = ordersRepository.save(Orders.create(authId, orderDestination)); // OrderDestination will be saved by cascading

        // Upload Image
        String imgUrl = s3Uploader.upload(imageFile);

        // Save CustomProduct
        CustomProduct customProduct = CustomProduct.create(imgUrl, dto.getProductDto().getQuantity(), authId);
        customProduct.associateWithProduct(product);
        customProduct.associateWithOrder(order);
        customProductRepository.save(customProduct);

        // Save CustomProductOption
        for (OptionDetail detail : optionDetails) {
            CustomProductOption customProductOption = CustomProductOption.create();
            customProductOption.associate(customProduct);
            customProductOption.associate(detail);
            customProductOptionRepository.save(customProductOption);
        }

        order.calcTotalAmountAndSet();
        order.calcTotalQuantityAndSet();

        return order;
    }

}