package com.liberty52.product.service.applicationservice;

import com.liberty52.product.global.adapter.S3Uploader;
import com.liberty52.product.global.exception.external.ResourceNotFoundException;
import com.liberty52.product.service.controller.dto.MonoItemOrderRequestDto;
import com.liberty52.product.service.controller.dto.MonoItemOrderResponseDto;
import com.liberty52.product.service.controller.dto.PreregisterOrderRequestDto;
import com.liberty52.product.service.controller.dto.PreregisterOrderResponseDto;
import com.liberty52.product.service.entity.*;
import com.liberty52.product.service.entity.payment.Payment;
import com.liberty52.product.service.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MonoItemOrderServiceImpl implements MonoItemOrderService {

    private static final String RESOURCE_NAME_PRODUCT = "Product";
    private static final String PARAM_NAME_PRODUCT_NAME = "name";
    private static final String RESOURCE_NAME_OPTION_DETAIL = "OptionDetail";
    private static final String PARAM_NAME_OPTION_DETAIL_NAME = "name";
    private final ProductRepository productRepository;
    private final S3Uploader s3Uploader;
    private final CustomProductRepository customProductRepository;
    private final OrdersRepository ordersRepository;
    private final OptionDetailRepository optionDetailRepository;
    private final CustomProductOptionRepository customProductOptionRepository;
    private final PaymentRepository paymentRepository;

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
        CustomProduct customProduct = customProductRepository.save(CustomProduct.create(imgUrl, dto.getQuantity(), authId));
        customProduct.associateWithProduct(product);
        customProduct.associateWithOrder(order);

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
        CustomProduct customProduct = customProductRepository.save(CustomProduct.create(imgUrl, dto.getProductDto().getQuantity(), authId));
        customProduct.associateWithProduct(product);
        customProduct.associateWithOrder(order);

        // Save CustomProductOption
        for (OptionDetail detail : optionDetails) {
            CustomProductOption customProductOption = customProductOptionRepository.save(CustomProductOption.create());
            customProductOption.associate(customProduct);
            customProductOption.associate(detail);
        }

        order.calcTotalAmountAndSet();

        paymentRepository.save(Payment.cardOf(order));

        return PreregisterOrderResponseDto.of(order.getId(), order.getAmount());
    }


}
