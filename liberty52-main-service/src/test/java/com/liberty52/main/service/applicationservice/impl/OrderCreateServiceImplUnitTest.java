package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.common.exception.external.badrequest.BadRequestException;
import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.global.adapter.s3.S3UploaderApi;
import com.liberty52.main.global.exception.external.badrequest.RequestForgeryPayException;
import com.liberty52.main.global.exception.external.forbidden.NotYourCustomProductException;
import com.liberty52.main.global.exception.external.forbidden.NotYourOrderException;
import com.liberty52.main.global.exception.external.internalservererror.ConfirmPaymentException;
import com.liberty52.main.global.exception.external.internalservererror.InternalServerErrorException;
import com.liberty52.main.global.util.Result;
import com.liberty52.main.global.util.ThreadManager;
import com.liberty52.main.service.applicationservice.OptionDetailMultipleStockManageService;
import com.liberty52.main.service.controller.dto.OrderCreateRequestDto;
import com.liberty52.main.service.controller.dto.PaymentCardResponseDto;
import com.liberty52.main.service.controller.dto.PaymentVBankResponseDto;
import com.liberty52.main.service.entity.CustomProduct;
import com.liberty52.main.service.entity.OptionDetail;
import com.liberty52.main.service.entity.Product;
import com.liberty52.main.service.entity.license.LicenseOption;
import com.liberty52.main.service.entity.license.LicenseOptionDetail;
import com.liberty52.main.service.entity.payment.CardPayment;
import com.liberty52.main.service.entity.payment.Payment;
import com.liberty52.main.service.repository.*;
import com.liberty52.main.service.utils.MockFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderCreateServiceImplUnitTest {

    @InjectMocks
    private OrderCreateServiceImpl service;
    @Mock
    private S3UploaderApi s3UploaderApi;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OptionDetailMultipleStockManageService optionDetailMultipleStockManageService;
    @Mock
    private CustomProductRepository customProductRepository;
    @Mock
    private OrdersRepository ordersRepository;
    @Mock
    private OptionDetailRepository optionDetailRepository;
    @Mock
    private CustomProductOptionRepository customProductOptionRepository;
    @Mock
    private ConfirmPaymentMapRepository confirmPaymentMapRepository;
    @Mock
    private VBankRepository vBankRepository;
    @Mock
    private ThreadManager threadManager;
    @Mock
    private LicenseOptionDetailRepository licenseOptionDetailRepository;
    @Mock
    private CustomLicenseOptionRepository customLicenseOptionRepository;

    private PaymentCardResponseDto executeCardPaymentOrders(String authId, List<String> options, int quantity,
        String licenseOptionDetailId) {
        return service.createCardPaymentOrders(
                authId,
                OrderCreateRequestDto.forTestCard("pn", options, quantity, List.of(),licenseOptionDetailId, "rn",
                        "re", "rpn", " ad1", "ad2", "zc"),
                null
        );
    }

    @Test
    @DisplayName("카드 결제 주문을 요청하여 주문을 생성한다")
    void createCardPaymentOrder() {
        // given
        var product = MockFactory.createProduct("pd");
        given(productRepository.findByName(anyString()))
                .willReturn(Optional.of(product));

        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 10, productOption)
        );
        optionDetails.forEach(it -> {
            given(optionDetailRepository.findById(it.getId()))
                    .willReturn(Optional.of(it));
        });
        given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                .willReturn(Result.success(optionDetails));

        var authId = "user_id";
        var order = MockFactory.createOrder(authId);
        given(ordersRepository.save(any())).willReturn(order);

        var imageSrc = "image_src";
        given(s3UploaderApi.upload(any())).willReturn(imageSrc);

        var customProduct = MockFactory.createCustomProduct(imageSrc, 1, authId, product, order);
        given(customProductRepository.save(any())).willReturn(customProduct);

        optionDetails.forEach(it -> {
            var cpo = MockFactory.createCustomProductOption(customProduct, it);
            given(customProductOptionRepository.save(any())).willReturn(cpo);
        });

        var options = optionDetails.stream().map(OptionDetail::getId).toList();
        // when
        var result = executeCardPaymentOrders(authId, options, 1, "");

        // then
        assertNotNull(result);
        assertEquals(order.getId(), result.getMerchantId());
        assertEquals(order.getOrderNum(), result.getOrderNum());
        assertEquals(order.getAmount(), result.getAmount());
    }

    @Test
    @DisplayName("라이선스 상품을 카드 주문을 요청하여 주문을 생성한다")
    void createCardPaymentOrderForLicenseProduct() {
        //given
        Product mockProduct = mock(Product.class);
        given(mockProduct.isCustom()).willReturn(false);
        given(productRepository.findByName(anyString())).willReturn(Optional.of(mockProduct));

        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
            MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
            MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
            MockFactory.createOptionDetail("od_3", 30000, 10, productOption)
        );
        optionDetails.forEach(it -> {
            given(optionDetailRepository.findById(it.getId()))
                .willReturn(Optional.of(it));
        });
        given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
            .willReturn(Result.success(optionDetails));

        LicenseOption licenseOption = MockFactory.createLicenseOption("licenseOption");
        LicenseOptionDetail licenseOptionDetail = MockFactory.createLicenseOptionDetail("licenseOptionDetail",
                "testArtist", 10, true, "testUrl", 10000, LocalDate.now(), LocalDate.now().plusDays(3), licenseOption);

        given(licenseOptionDetailRepository.findById(any()))
                .willReturn(Optional.of(licenseOptionDetail));

        var authId = "user_id";

        CustomProduct customProduct = MockFactory.createCustomProduct("", 1, authId, mockProduct);
        given(customProductRepository.save(any())).willReturn(customProduct);

        var order = MockFactory.createOrder(authId);
        given(ordersRepository.save(any())).willReturn(order);

        given(optionDetailMultipleStockManageService.decrementLicense(any(), anyInt()))
            .willReturn(Result.success(licenseOptionDetail));

        optionDetails.forEach(it -> {
            var cpo = MockFactory.createCustomProductOption(customProduct, it);
            given(customProductOptionRepository.save(any())).willReturn(cpo);
        });

        var options = optionDetails.stream().map(OptionDetail::getId).toList();

        // when
        var result = executeCardPaymentOrders(authId, options, 1, licenseOptionDetail.getId());

        // then
        assertNotNull(result);
        assertEquals(order.getId(), result.getMerchantId());
        assertEquals(order.getOrderNum(), result.getOrderNum());
        assertEquals(order.getAmount(), result.getAmount());
    }


    @Test
    @DisplayName("존재하지 않는 상품을 카드결제 주문할 경우 예외가 발생한다")
    void createCardPaymentOrder_when_notFoundProduct() {
        // given
        var authId = "user_id";
        var options = List.of("not", "found", "product");
        given(productRepository.findByName(anyString()))
                .willReturn(Optional.empty());
        // when
        // then
        assertThrows(
                ResourceNotFoundException.class,
                () -> executeCardPaymentOrders(authId, options, 1, "")
        );
    }

    @Test
    @DisplayName("존재하지 않는 상품 옵션을 카드결제 주문할 경우 예외가 발생한다")
    void createCardPaymentOrder_when_notFoundOptionDetail() {
        // given
        var authId = "user_id";
        var product = MockFactory.createProduct("pd");
        given(productRepository.findByName(anyString()))
                .willReturn(Optional.of(product));
        given(optionDetailRepository.findById(anyString()))
                .willReturn(Optional.empty());
        var options = List.of("not", "found", "optionDetail");
        // when
        // then
        assertThrows(
                ResourceNotFoundException.class,
                () -> executeCardPaymentOrders(authId, options, 1, "")
        );
    }

    @Test
    @DisplayName("재고량이 0 이하인 상품 옵션을 카드결제 주문할 경우 예외가 발생한다")
    void createCardPaymentOrder_when_noMoreStockOfOptionDetail() {
        // given
        var authId = "user_id";
        var product = MockFactory.createProduct("pd");
        given(productRepository.findByName(anyString()))
                .willReturn(Optional.of(product));
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 0, productOption)
        );
        optionDetails.forEach(it -> {
            lenient().when(optionDetailRepository.findById(it.getId()))
                    .thenReturn(Optional.of(it));
        });
        given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                .willReturn(Result.failure(new BadRequestException("")));
        var options = optionDetails.stream().map(OptionDetail::getId).toList();
        // when
        // then
        assertThrows(
                BadRequestException.class,
                () -> executeCardPaymentOrders(authId, options, 1, "")
        );
    }

    @Test
    @DisplayName("재고량이 1인 상품 옵션을 카드결제로 2개 수량 주문할 경우 예외가 발생한다")
    void createCardPaymentOrder_when_noMoreStockForTwoQuantity() {
        // given
        var authId = "user_id";
        var product = MockFactory.createProduct("pd");
        given(productRepository.findByName(anyString()))
                .willReturn(Optional.of(product));
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 1, productOption)
        );
        optionDetails.forEach(it -> {
            lenient().when(optionDetailRepository.findById(it.getId()))
                    .thenReturn(Optional.of(it));
        });
        given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                .willReturn(Result.failure(new BadRequestException("")));
        var options = optionDetails.stream().map(OptionDetail::getId).toList();
        // when
        // then
        assertThrows(
                BadRequestException.class,
                () -> executeCardPaymentOrders(authId, options, 2, "")
        );
    }

    private PaymentVBankResponseDto executeVBankPaymentOrders(String authId, List<String> options, int quantity) {
        return service.createVBankPaymentOrders(
                authId,
                OrderCreateRequestDto.forTestVBank("pn", options, quantity, List.of(), "","rn",
                        "re", "rpn", " ad1", "ad2", "zc",
                        "하나은행 1234 holder", "dn"),
                null
        );
    }

    @Test
    @DisplayName("가상계좌 결제 주문을 요청하여 주문을 생성한다")
    void createVBankPaymentOrders() {
        // given
        var product = MockFactory.createProduct("pd");
        given(productRepository.findByName(anyString()))
                .willReturn(Optional.of(product));

        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 10, productOption)
        );
        optionDetails.forEach(it -> {
            given(optionDetailRepository.findById(it.getId()))
                    .willReturn(Optional.of(it));
        });
        given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                .willReturn(Result.success(optionDetails));

        var authId = "user_id";
        var order = MockFactory.createOrder(authId);
        given(ordersRepository.save(any())).willReturn(order);

        var imageSrc = "image_src";
        given(s3UploaderApi.upload(any())).willReturn(imageSrc);

        var customProduct = MockFactory.createCustomProduct(imageSrc, 1, authId, product, order);
        given(customProductRepository.save(any())).willReturn(customProduct);

        optionDetails.forEach(it -> {
            var cpo = MockFactory.createCustomProductOption(customProduct, it);
            given(customProductOptionRepository.save(any())).willReturn(cpo);
        });

        given(vBankRepository.existsByBankAndAccountAndHolder(any(), anyString(), anyString()))
                .willReturn(true);

        var options = optionDetails.stream().map(OptionDetail::getId).toList();

        // when
        var result = executeVBankPaymentOrders(authId, options, 1);

        // then
        assertNotNull(result);
        assertEquals(order.getId(), result.getOrderId());
        assertEquals(order.getOrderNum(), result.getOrderNum());
    }

    @Test
    @DisplayName("존재하지 않는 상품을 가상계좌 결제 주문할 경우 예외가 발생한다")
    void createVBankPaymentOrders_when_notFoundProduct() {
        // given
        var authId = "user_id";
        given(productRepository.findByName(anyString()))
                .willReturn(Optional.empty());
        // when
        // then
        assertThrows(
                ResourceNotFoundException.class,
                () -> executeVBankPaymentOrders(authId, List.of("not", "found", "product"), 1)
        );
    }

    @Test
    @DisplayName("존재하지 않는 상품 옵션을 가상계좌 결제 주문할 경우 예외가 발생한다")
    void createVBankPaymentOrders_when_notFoundOptionDetail() {
        // given
        var authId = "user_id";
        var product = MockFactory.createProduct("pd");
        given(productRepository.findByName(anyString()))
                .willReturn(Optional.of(product));
        given(optionDetailRepository.findById(anyString()))
                .willReturn(Optional.empty());
        // when
        // then
        assertThrows(
                ResourceNotFoundException.class,
                () -> executeVBankPaymentOrders(authId, List.of("not", "found", "od"), 1)
        );
    }

    @Test
    @DisplayName("재고량이 0 이하인 상품 옵션을 가상계좌 결제 주문할 경우 예외가 발생한다")
    void createVBankPaymentOrders_when_noMoreStockOfOptionDetail() {
        // given
        var authId = "user_id";
        var product = MockFactory.createProduct("pd");
        given(productRepository.findByName(anyString()))
                .willReturn(Optional.of(product));

        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 0, productOption)
        );
        optionDetails.forEach(it -> {
            lenient().when(optionDetailRepository.findById(it.getId()))
                    .thenReturn(Optional.of(it));
        });
        given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                .willReturn(Result.failure(new BadRequestException("")));

        var options = optionDetails.stream().map(OptionDetail::getId).toList();
        // when
        // then
        assertThrows(
                BadRequestException.class,
                () -> executeVBankPaymentOrders(authId, options, 1)
        );
    }

    @Test
    @DisplayName("재고량이 1인 상품 옵션을 가상계좌 결제로 2개 수량 주문할 경우 예외가 발생한다")
    void createVBankPaymentOrder_when_noMoreStockForTwoQuantity() {
        // given
        var authId = "user_id";
        var product = MockFactory.createProduct("pd");
        given(productRepository.findByName(anyString()))
                .willReturn(Optional.of(product));
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 1, productOption)
        );
        optionDetails.forEach(it -> {
            lenient().when(optionDetailRepository.findById(it.getId()))
                    .thenReturn(Optional.of(it));
        });
        given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                .willReturn(Result.failure(new BadRequestException("")));
        var options = optionDetails.stream().map(OptionDetail::getId).toList();
        // when
        // then
        assertThrows(
                BadRequestException.class,
                () -> executeVBankPaymentOrders(authId, options, 2)
        );
    }

    private PaymentCardResponseDto executeCardPaymentOrderByCarts(String authId, List<String> customProductIdList) {
        return service.createCardPaymentOrdersByCarts(
                authId,
                OrderCreateRequestDto.forTestCardByCarts(
                        customProductIdList,
                        "rn", "re", "rpn", "ad1", "ad2", "zc"
                )
        );
    }

    @Test
    @DisplayName("장바구니에서 카드 결제 주문을 요청하여 주문을 생성한다")
    void createCardPaymentOrderByCarts() {
        // given
        var authId = "user_id";

        var product = MockFactory.createProduct("pd");
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 10, productOption)
        );

        var imageUrl = "image_url";
        var customProducts = List.of(
                MockFactory.createCustomProduct(imageUrl, 1, authId, product),
                MockFactory.createCustomProduct(imageUrl, 1, authId, product)
        );
        customProducts.forEach(cp -> {
            optionDetails.forEach(od -> {
                MockFactory.createCustomProductOption(cp, od);
            });
            given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                    .willReturn(Result.success(optionDetails));
            given(customProductRepository.findById(cp.getId()))
                    .willReturn(Optional.of(cp));
        });


        var order = MockFactory.createOrder(authId);
        given(ordersRepository.save(any())).willReturn(order);

        var customProductIdList = customProducts.stream().map(CustomProduct::getId).toList();

        // when
        var result = executeCardPaymentOrderByCarts(authId, customProductIdList);

        // then
        assertNotNull(result);
        assertEquals(order.getId(), result.getMerchantId());
        assertEquals(order.getOrderNum(), result.getOrderNum());
        assertEquals(order.getAmount(), result.getAmount());
    }

    @Test
    @DisplayName("존재하지 않는 커스텀 상품을 장바구니에서 카드 결제 주문할 경우 예외가 발생한다")
    void createCardPaymentOrderByCarts_when_notfoundcustomproduct() {
        // given
        var authId = "user_id";
        given(customProductRepository.findById(anyString()))
                .willReturn(Optional.empty());
        // when
        // then
        assertThrows(
                ResourceNotFoundException.class,
                () -> executeCardPaymentOrderByCarts(authId, List.of("not", "found", "cp"))
        );
    }

    @Test
    @DisplayName("요청 유저의 커스텀 상품이 아닌 상품을 장바구니에서 카드 결제 주문할 경우 예외가 발생한다")
    void createCardPaymentOrderByCarts_when_forbidden() {
        // given
        var authId = "user_id";

        var product = MockFactory.createProduct("pd");
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 10, productOption)
        );

        var imageUrl = "image_url";
        var customProducts = List.of(
                MockFactory.createCustomProduct(imageUrl, 1, authId, product),
                MockFactory.createCustomProduct(imageUrl, 1, "not_yours", product)
        );
        customProducts.forEach(cp -> {
            optionDetails.forEach(od -> {
                MockFactory.createCustomProductOption(cp, od);
            });
            given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                    .willReturn(Result.success(optionDetails));
            given(customProductRepository.findById(cp.getId()))
                    .willReturn(Optional.of(cp));
        });

        // when
        // then
        var cpIds = customProducts.stream().map(CustomProduct::getId).toList();
        assertThrows(
                NotYourCustomProductException.class,
                () -> executeCardPaymentOrderByCarts(authId, cpIds)
        );
    }

    @Test
    @DisplayName("재고량이 0 이하인 상품 옵션을 장바구니에서 카드 결제 주문할 경우 예외가 발생한다")
    void createCardPaymentOrderByCarts_when_noMoreStockOfOption() {
        // given
        var authId = "user_id";

        var product = MockFactory.createProduct("pd");
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 0, productOption)
        );

        var imageUrl = "image_url";
        var customProducts = List.of(
                MockFactory.createCustomProduct(imageUrl, 1, authId, product),
                MockFactory.createCustomProduct(imageUrl, 1, authId, product)
        );
        customProducts.forEach(cp -> {
            optionDetails.forEach(od -> MockFactory.createCustomProductOption(cp, od));
            lenient().when(customProductRepository.findById(cp.getId()))
                    .thenReturn(Optional.of(cp));
        });
        given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                .willReturn(Result.failure(new BadRequestException("")));
        // when
        // then
        var cpIds = customProducts.stream().map(CustomProduct::getId).toList();
        assertThrows(
                BadRequestException.class,
                () -> executeCardPaymentOrderByCarts(authId, cpIds)
        );
    }

    @Test
    @DisplayName("재고량이 1인 상품 옵션을 동시에 장바구니에서 2개를 카드결제 주문할 경우 예외가 발생한다")
    void createCardPaymentOrderByCarts_when_noMoreStockOfOption_duringOrderOfCarts() {
        // given
        var authId = "user_id";

        var product = MockFactory.createProduct("pd");
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 1, productOption)
        );

        var imageUrl = "image_url";
        var customProducts = List.of(
                MockFactory.createCustomProduct(imageUrl, 1, authId, product),
                MockFactory.createCustomProduct(imageUrl, 1, authId, product)
        );
        customProducts.forEach(cp -> {
            optionDetails.forEach(od -> MockFactory.createCustomProductOption(cp, od));
            lenient().when(customProductRepository.findById(cp.getId()))
                    .thenReturn(Optional.of(cp));
        });
        given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                .willReturn(Result.failure(new BadRequestException("")));
        // when
        // then
        var cpIds = customProducts.stream().map(CustomProduct::getId).toList();
        assertThrows(
                BadRequestException.class,
                () -> executeCardPaymentOrderByCarts(authId, cpIds)
        );
    }

    @Test
    @DisplayName("재고량이 1인 상품 옵션을 장바구니에서 카드결제로 2개 수량 주문할 경우 예외가 발생한다")
    void createCardPaymentOrderByCarts_when_noMoreStockForTwoQuantity() {
        // given
        var authId = "user_id";
        var product = MockFactory.createProduct("pd");
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 1, productOption)
        );

        var imageUrl = "image_url";
        var customProducts = List.of(
                MockFactory.createCustomProduct(imageUrl, 2, authId, product)
        );
        customProducts.forEach(cp -> {
            optionDetails.forEach(od -> MockFactory.createCustomProductOption(cp, od));
            lenient().when(customProductRepository.findById(cp.getId()))
                    .thenReturn(Optional.of(cp));
        });
        given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                .willReturn(Result.failure(new BadRequestException("")));
        // when
        // then
        var cpIds = customProducts.stream().map(CustomProduct::getId).toList();
        assertThrows(
                BadRequestException.class,
                () -> executeCardPaymentOrderByCarts(authId, cpIds)
        );
    }

    private PaymentVBankResponseDto executeVBankPaymentOrderByCarts(String authId, List<String> customProductIdList) {
        return service.createVBankPaymentOrdersByCarts(
                authId,
                OrderCreateRequestDto.forTestVBankByCarts(
                        customProductIdList,
                        "rn", "re", "rpn", "ad1", "ad2", "zc",
                        "하나은행 1234 holder", "dn"
                )
        );
    }

    @Test
    @DisplayName("장바구니에서 가상계좌 결제 주문을 요청하여 주문을 생성한다")
    void createVBankPaymentOrdersByCarts() {
        // given
        var authId = "user_id";

        var product = MockFactory.createProduct("pd");
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 10, productOption)
        );

        var imageUrl = "image_url";
        var customProducts = List.of(
                MockFactory.createCustomProduct(imageUrl, 1, authId, product),
                MockFactory.createCustomProduct(imageUrl, 1, authId, product)
        );
        customProducts.forEach(cp -> {
            optionDetails.forEach(od -> {
                MockFactory.createCustomProductOption(cp, od);
            });
            given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                    .willReturn(Result.success(optionDetails));
            given(customProductRepository.findById(cp.getId()))
                    .willReturn(Optional.of(cp));
        });

        var order = MockFactory.createOrder(authId);
        given(ordersRepository.save(any())).willReturn(order);

        given(vBankRepository.existsByBankAndAccountAndHolder(any(), anyString(), anyString()))
                .willReturn(true);

        var customProductIdList = customProducts.stream().map(CustomProduct::getId).toList();

        // when
        var result = executeVBankPaymentOrderByCarts(authId, customProductIdList);

        // then
        assertNotNull(result);
        assertEquals(order.getId(), result.getOrderId());
        assertEquals(order.getOrderNum(), result.getOrderNum());
    }

    @Test
    @DisplayName("존재하지 않는 커스텀 상품을 장바구니에서 가상계좌 결제 주문할 경우 예외가 발생한다")
    void createVBankPaymentOrderByCarts_when_notfoundcustomproduct() {
        // given
        var authId = "user_id";
        given(customProductRepository.findById(anyString()))
                .willReturn(Optional.empty());
        // when
        // then
        assertThrows(
                ResourceNotFoundException.class,
                () -> executeVBankPaymentOrderByCarts(authId, List.of("not", "found", "cp"))
        );
    }

    @Test
    @DisplayName("요청 유저의 커스텀 상품이 아닌 상품을 장바구니에서 가상계좌 결제 주문할 경우 예외가 발생한다")
    void createVBankPaymentOrderByCarts_when_forbidden() {
        // given
        var authId = "user_id";

        var product = MockFactory.createProduct("pd");
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 10, productOption)
        );

        var imageUrl = "image_url";
        var customProducts = List.of(
                MockFactory.createCustomProduct(imageUrl, 1, authId, product),
                MockFactory.createCustomProduct(imageUrl, 1, "not_yours", product)
        );
        customProducts.forEach(cp -> {
            optionDetails.forEach(od -> {
                MockFactory.createCustomProductOption(cp, od);
            });
            given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                    .willReturn(Result.success(optionDetails));
            given(customProductRepository.findById(cp.getId()))
                    .willReturn(Optional.of(cp));
        });

        // when
        // then
        var cpIds = customProducts.stream().map(CustomProduct::getId).toList();
        assertThrows(
                NotYourCustomProductException.class,
                () -> executeVBankPaymentOrderByCarts(authId, cpIds)
        );
    }

    @Test
    @DisplayName("재고량이 0 이하인 상품 옵션을 장바구니에서 가상계좌 결제 주문할 경우 예외가 발생한다")
    void createVBankPaymentOrderByCarts_when_noMoreStockOfOption() {
        // given
        var authId = "user_id";

        var product = MockFactory.createProduct("pd");
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 0, productOption)
        );

        var imageUrl = "image_url";
        var customProducts = List.of(
                MockFactory.createCustomProduct(imageUrl, 1, authId, product),
                MockFactory.createCustomProduct(imageUrl, 1, authId, product)
        );
        customProducts.forEach(cp -> {
            optionDetails.forEach(od -> MockFactory.createCustomProductOption(cp, od));
            lenient().when(customProductRepository.findById(cp.getId()))
                    .thenReturn(Optional.of(cp));
        });
        given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                .willReturn(Result.failure(new BadRequestException("")));
        // when
        // then
        var cpIds = customProducts.stream().map(CustomProduct::getId).toList();
        assertThrows(
                BadRequestException.class,
                () -> executeVBankPaymentOrderByCarts(authId, cpIds)
        );
    }

    @Test
    @DisplayName("재고량이 1인 상품 옵션을 동시에 장바구니에서 2개를 가상계좌 결제 주문할 경우 예외가 발생한다")
    void createVBankPaymentOrderByCarts_when_noMoreStockOfOption_duringOrderOfCarts() {
        // given
        var authId = "user_id";

        var product = MockFactory.createProduct("pd");
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 1, productOption)
        );

        var imageUrl = "image_url";
        var customProducts = List.of(
                MockFactory.createCustomProduct(imageUrl, 1, authId, product),
                MockFactory.createCustomProduct(imageUrl, 1, authId, product)
        );
        customProducts.forEach(cp -> {
            optionDetails.forEach(od -> MockFactory.createCustomProductOption(cp, od));
            lenient().when(customProductRepository.findById(cp.getId()))
                    .thenReturn(Optional.of(cp));
        });
        given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                .willReturn(Result.failure(new BadRequestException("")));
        // when
        // then
        var cpIds = customProducts.stream().map(CustomProduct::getId).toList();
        assertThrows(
                BadRequestException.class,
                () -> executeVBankPaymentOrderByCarts(authId, cpIds)
        );
    }

    @Test
    @DisplayName("재고량이 1인 상품 옵션을 장바구니에서 가상계좌 결제로 2개 수량 주문할 경우 예외가 발생한다")
    void createVBankPaymentOrderByCarts_when_noMoreStockForTwoQuantity() {
        // given
        var authId = "user_id";
        var product = MockFactory.createProduct("pd");
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 1, productOption)
        );

        var imageUrl = "image_url";
        var customProducts = List.of(
                MockFactory.createCustomProduct(imageUrl, 2, authId, product)
        );
        customProducts.forEach(cp -> {
            optionDetails.forEach(od -> MockFactory.createCustomProductOption(cp, od));
            lenient().when(customProductRepository.findById(cp.getId()))
                    .thenReturn(Optional.of(cp));
        });
        given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                .willReturn(Result.failure(new BadRequestException("")));
        // when
        // then
        var cpIds = customProducts.stream().map(CustomProduct::getId).toList();
        assertThrows(
                BadRequestException.class,
                () -> executeVBankPaymentOrderByCarts(authId, cpIds)
        );
    }

    private PaymentCardResponseDto executeCardPaymentOrderByCartsForGuest(String authId, List<String> customProductIdList) {
        return service.createCardPaymentOrdersByCartsForGuest(
                authId,
                OrderCreateRequestDto.forTestCardByCarts(
                        customProductIdList,
                        "rn", "re", "rpn", "ad1", "ad2", "zc"
                )
        );
    }

    @Test
    @DisplayName("비회원 유저가 장바구니에서 카드 결제 주문을 요청하여 주문을 생성한다")
    void createCardPaymentOrdersByCartsForGuest() {
        // given
        var authId = "anonymous_user";

        var product = MockFactory.createProduct("pd");
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 10, productOption)
        );

        var imageUrl = "image_url";
        var customProducts = List.of(
                MockFactory.createCustomProduct(imageUrl, 1, authId, product),
                MockFactory.createCustomProduct(imageUrl, 1, authId, product)
        );
        customProducts.forEach(cp -> {
            optionDetails.forEach(od -> {
                MockFactory.createCustomProductOption(cp, od);
            });
            given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                    .willReturn(Result.success(optionDetails));
            given(customProductRepository.findById(cp.getId()))
                    .willReturn(Optional.of(cp));
        });

        var order = MockFactory.createOrder(authId);
        given(ordersRepository.save(any())).willReturn(order);

        var customProductIdList = customProducts.stream().map(CustomProduct::getId).toList();

        // when
        var result = executeCardPaymentOrderByCartsForGuest(authId, customProductIdList);

        // then
        assertNotNull(result);
        assertEquals(order.getId(), result.getMerchantId());
        assertEquals(order.getOrderNum(), result.getOrderNum());
        assertEquals(order.getAmount(), result.getAmount());
    }

    @Test
    @DisplayName("비회원 유저가 존재하지 않는 커스텀 상품을 장바구니에서 카드 결제 주문할 경우 예외가 발생한다")
    void createCardPaymentOrdersByCartsForGuest_when_notfoundcustomproduct() {
        // given
        var authId = "user_id";
        given(customProductRepository.findById(anyString()))
                .willReturn(Optional.empty());
        // when
        // then
        assertThrows(
                ResourceNotFoundException.class,
                () -> executeCardPaymentOrderByCartsForGuest(authId, List.of("not", "found", "cp"))
        );
    }

    @Test
    @DisplayName("비회원 요청 유저의 커스텀 상품이 아닌 상품을 장바구니에서 카드 결제 주문할 경우 예외가 발생한다")
    void createCardPaymentOrdersByCartsForGuest_when_forbidden() {
        // given
        var authId = "user_id";

        var product = MockFactory.createProduct("pd");
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 10, productOption)
        );

        var imageUrl = "image_url";
        var customProducts = List.of(
                MockFactory.createCustomProduct(imageUrl, 1, authId, product),
                MockFactory.createCustomProduct(imageUrl, 1, "not_yours", product)
        );
        customProducts.forEach(cp -> {
            optionDetails.forEach(od -> {
                MockFactory.createCustomProductOption(cp, od);
            });
            given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                    .willReturn(Result.success(optionDetails));
            given(customProductRepository.findById(cp.getId()))
                    .willReturn(Optional.of(cp));
        });

        // when
        // then
        var cpIds = customProducts.stream().map(CustomProduct::getId).toList();
        assertThrows(
                NotYourCustomProductException.class,
                () -> executeCardPaymentOrderByCartsForGuest(authId, cpIds)
        );
    }

    @Test
    @DisplayName("비회원 유저가 재고량이 0 이하인 상품 옵션을 장바구니에서 카드 결제 주문할 경우 예외가 발생한다")
    void createCardPaymentOrdersByCartsForGuest_when_noMoreStockOfOption() {
        // given
        var authId = "user_id";

        var product = MockFactory.createProduct("pd");
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 0, productOption)
        );

        var imageUrl = "image_url";
        var customProducts = List.of(
                MockFactory.createCustomProduct(imageUrl, 1, authId, product),
                MockFactory.createCustomProduct(imageUrl, 1, authId, product)
        );
        customProducts.forEach(cp -> {
            optionDetails.forEach(od -> MockFactory.createCustomProductOption(cp, od));
            lenient().when(customProductRepository.findById(cp.getId()))
                    .thenReturn(Optional.of(cp));
        });
        given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                .willReturn(Result.failure(new BadRequestException("")));
        // when
        // then
        var cpIds = customProducts.stream().map(CustomProduct::getId).toList();
        assertThrows(
                BadRequestException.class,
                () -> executeCardPaymentOrderByCartsForGuest(authId, cpIds)
        );
    }

    @Test
    @DisplayName("비회원 유저가 재고량이 1인 상품 옵션을 동시에 장바구니에서 2개를 카드 결제 주문할 경우 예외가 발생한다")
    void createCardPaymentOrdersByCartsForGuest_when_noMoreStockOfOption_duringOrderOfCarts() {
        // given
        var authId = "user_id";

        var product = MockFactory.createProduct("pd");
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 1, productOption)
        );

        var imageUrl = "image_url";
        var customProducts = List.of(
                MockFactory.createCustomProduct(imageUrl, 1, authId, product),
                MockFactory.createCustomProduct(imageUrl, 1, authId, product)
        );
        customProducts.forEach(cp -> {
            optionDetails.forEach(od -> MockFactory.createCustomProductOption(cp, od));
            lenient().when(customProductRepository.findById(cp.getId()))
                    .thenReturn(Optional.of(cp));
        });
        given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                .willReturn(Result.failure(new BadRequestException("")));
        // when
        // then
        var cpIds = customProducts.stream().map(CustomProduct::getId).toList();
        assertThrows(
                BadRequestException.class,
                () -> executeCardPaymentOrderByCartsForGuest(authId, cpIds)
        );
    }

    @Test
    @DisplayName("비회원 유저가 재고량이 1인 상품 옵션을 장바구니에서 카드 결제로 2개 수량 주문할 경우 예외가 발생한다")
    void createCardPaymentOrdersByCartsForGuest_when_noMoreStockForTwoQuantity() {
        // given
        var authId = "user_id";
        var product = MockFactory.createProduct("pd");
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 1, productOption)
        );

        var imageUrl = "image_url";
        var customProducts = List.of(
                MockFactory.createCustomProduct(imageUrl, 2, authId, product)
        );
        customProducts.forEach(cp -> {
            optionDetails.forEach(od -> MockFactory.createCustomProductOption(cp, od));
            lenient().when(customProductRepository.findById(cp.getId()))
                    .thenReturn(Optional.of(cp));
        });
        given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                .willReturn(Result.failure(new BadRequestException("")));
        // when
        // then
        var cpIds = customProducts.stream().map(CustomProduct::getId).toList();
        assertThrows(
                BadRequestException.class,
                () -> executeCardPaymentOrderByCartsForGuest(authId, cpIds)
        );
    }

    private PaymentVBankResponseDto executeVBankPaymentOrdersByCartsForGuest(String authId, List<String> customProductIdList) {
        return service.createVBankPaymentOrdersByCartsForGuest(
                authId,
                OrderCreateRequestDto.forTestVBankByCarts(
                        customProductIdList,
                        "rn", "re", "rpn", "ad1", "ad2", "zc",
                        "하나은행 1234 holder", "dn"
                )
        );
    }

    @Test
    @DisplayName("비회원유저가 장바구니에서 가상계좌 결제 주문을 요청하여 주문을 생성한다")
    void createVBankPaymentOrdersByCartsForGuest() {
        // given
        var authId = "anonymous_user";

        var product = MockFactory.createProduct("pd");
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 10, productOption)
        );

        var imageUrl = "image_url";
        var customProducts = List.of(
                MockFactory.createCustomProduct(imageUrl, 1, authId, product),
                MockFactory.createCustomProduct(imageUrl, 1, authId, product)
        );
        customProducts.forEach(cp -> {
            optionDetails.forEach(od -> {
                MockFactory.createCustomProductOption(cp, od);
            });
            given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                    .willReturn(Result.success(optionDetails));
            given(customProductRepository.findById(cp.getId()))
                    .willReturn(Optional.of(cp));
        });

        var order = MockFactory.createOrder(authId);
        given(ordersRepository.save(any())).willReturn(order);

        given(vBankRepository.existsByBankAndAccountAndHolder(any(), anyString(), anyString()))
                .willReturn(true);

        var customProductIdList = customProducts.stream().map(CustomProduct::getId).toList();

        // when
        var result = executeVBankPaymentOrdersByCartsForGuest(authId, customProductIdList);

        // then
        assertNotNull(result);
        assertEquals(order.getId(), result.getOrderId());
        assertEquals(order.getOrderNum(), result.getOrderNum());
    }

    @Test
    @DisplayName("비회원 유저가 존재하지 않는 커스텀 상품을 장바구니에서 가상계좌 결제 주문할 경우 예외가 발생한다")
    void createVBankPaymentOrdersByCartsForGuest_when_notfoundcustomproduct() {
        // given
        var authId = "user_id";
        given(customProductRepository.findById(anyString()))
                .willReturn(Optional.empty());
        // when
        // then
        assertThrows(
                ResourceNotFoundException.class,
                () -> executeVBankPaymentOrdersByCartsForGuest(authId, List.of("not", "found", "cp"))
        );
    }

    @Test
    @DisplayName("비회원 요청 유저의 커스텀 상품이 아닌 상품을 장바구니에서 가상계좌 결제 주문할 경우 예외가 발생한다")
    void createVBankPaymentOrdersByCartsForGuest_when_forbidden() {
        // given
        var authId = "user_id";

        var product = MockFactory.createProduct("pd");
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 10, productOption)
        );

        var imageUrl = "image_url";
        var customProducts = List.of(
                MockFactory.createCustomProduct(imageUrl, 1, authId, product),
                MockFactory.createCustomProduct(imageUrl, 1, "not_yours", product)
        );
        customProducts.forEach(cp -> {
            optionDetails.forEach(od -> {
                MockFactory.createCustomProductOption(cp, od);
            });
            given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                    .willReturn(Result.success(optionDetails));
            given(customProductRepository.findById(cp.getId()))
                    .willReturn(Optional.of(cp));
        });

        // when
        // then
        var cpIds = customProducts.stream().map(CustomProduct::getId).toList();
        assertThrows(
                NotYourCustomProductException.class,
                () -> executeVBankPaymentOrdersByCartsForGuest(authId, cpIds)
        );
    }

    @Test
    @DisplayName("비회원 유저가 재고량이 0 이하인 상품 옵션을 장바구니에서 가상계좌 결제 주문할 경우 예외가 발생한다")
    void createVBankPaymentOrdersByCartsForGuest_when_noMoreStockOfOption() {
        // given
        var authId = "user_id";

        var product = MockFactory.createProduct("pd");
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 0, productOption)
        );

        var imageUrl = "image_url";
        var customProducts = List.of(
                MockFactory.createCustomProduct(imageUrl, 1, authId, product),
                MockFactory.createCustomProduct(imageUrl, 1, authId, product)
        );
        customProducts.forEach(cp -> {
            optionDetails.forEach(od -> MockFactory.createCustomProductOption(cp, od));
            lenient().when(customProductRepository.findById(cp.getId()))
                    .thenReturn(Optional.of(cp));
        });
        given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                .willReturn(Result.failure(new BadRequestException("")));
        // when
        // then
        var cpIds = customProducts.stream().map(CustomProduct::getId).toList();
        assertThrows(
                BadRequestException.class,
                () -> executeVBankPaymentOrdersByCartsForGuest(authId, cpIds)
        );
    }

    @Test
    @DisplayName("비회원 유저가 재고량이 1인 상품 옵션을 동시에 장바구니에서 2개를 가상계좌 결제 주문할 경우 예외가 발생한다")
    void createVBankPaymentOrdersByCartsForGuest_when_noMoreStockOfOption_duringOrderOfCarts() {
        // given
        var authId = "user_id";

        var product = MockFactory.createProduct("pd");
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 1, productOption)
        );

        var imageUrl = "image_url";
        var customProducts = List.of(
                MockFactory.createCustomProduct(imageUrl, 1, authId, product),
                MockFactory.createCustomProduct(imageUrl, 1, authId, product)
        );
        customProducts.forEach(cp -> {
            optionDetails.forEach(od -> MockFactory.createCustomProductOption(cp, od));
            lenient().when(customProductRepository.findById(cp.getId()))
                    .thenReturn(Optional.of(cp));
        });
        given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                .willReturn(Result.failure(new BadRequestException("")));
        // when
        // then
        var cpIds = customProducts.stream().map(CustomProduct::getId).toList();
        assertThrows(
                BadRequestException.class,
                () -> executeVBankPaymentOrdersByCartsForGuest(authId, cpIds)
        );
    }

    @Test
    @DisplayName("비회원 유저가 재고량이 1인 상품 옵션을 장바구니에서 가상계좌 결제로 2개 수량 주문할 경우 예외가 발생한다")
    void createVBankPaymentOrdersByCartsForGuest_when_noMoreStockForTwoQuantity() {
        // given
        var authId = "user_id";
        var product = MockFactory.createProduct("pd");
        var productOption = MockFactory.createProductOption("po", true);
        var optionDetails = List.of(
                MockFactory.createOptionDetail("od_1", 10000, 10, productOption),
                MockFactory.createOptionDetail("od_2", 20000, 10, productOption),
                MockFactory.createOptionDetail("od_3", 30000, 1, productOption)
        );

        var imageUrl = "image_url";
        var customProducts = List.of(
                MockFactory.createCustomProduct(imageUrl, 2, authId, product)
        );
        customProducts.forEach(cp -> {
            optionDetails.forEach(od -> MockFactory.createCustomProductOption(cp, od));
            lenient().when(customProductRepository.findById(cp.getId()))
                    .thenReturn(Optional.of(cp));
        });
        given(optionDetailMultipleStockManageService.decrement(anyList(), anyInt()))
                .willReturn(Result.failure(new BadRequestException("")));
        // when
        // then
        var cpIds = customProducts.stream().map(CustomProduct::getId).toList();
        assertThrows(
                BadRequestException.class,
                () -> executeVBankPaymentOrdersByCartsForGuest(authId, cpIds)
        );
    }

    @Test
    @DisplayName("카드결제 최종 승인을 요청하여 카드 결제를 완료한다")
    void confirmFinalApprovalOfCardPayment() {
        // given
        given(confirmPaymentMapRepository.containsOrderId(anyString()))
                .willReturn(true);

        var authId = "authId";

        var payment = Payment.cardOf();
        payment.changeStatusToPaid();
        payment.setInfo(CardPayment.CardPaymentInfo.of("impUid", "pgProvider", "pgTid",
                LocalDateTime.now(), "cardName", "cardNumber", 1));

        var order = MockFactory.createOrder(authId);
        order.setPayment(payment);
        order.calculateTotalValueAndSet();
        given(confirmPaymentMapRepository.getAndRemove(anyString()))
                .willReturn(order);

        given(ordersRepository.findById(anyString()))
                .willReturn(Optional.of(order));

        // when
        var result = service.confirmFinalApprovalOfCardPayment(authId, order.getId());

        // then
        assertNotNull(result);
        assertEquals(order.getId(), result.getOrderId());
        assertEquals(order.getOrderNum(), result.getOrderNum());
    }

    @Test
    @DisplayName("카드결제 최종 승인 중 1분동안 카드 결제 정보가 확인되지 않는 경우 예외가 발생한다")
    void confirmFinalApprovalOfCardPayment_when_noResultDuringOneMinute() {
        // given
        given(confirmPaymentMapRepository.containsOrderId(anyString()))
                .willReturn(false);
        given(threadManager.sleep(anyLong()))
                .willReturn(true);
        // when
        // then
        assertThrows(
                ConfirmPaymentException.class,
                () -> service.confirmFinalApprovalOfCardPayment("user_id", "order_id")
        );
    }

    @Test
    @DisplayName("카드결제 최종 승인 중 스레드 인터럽트 에러가 발생할 경우 예외가 발생한다")
    void confirmFinalApprovalOfCardPayment_when_executeInterruptException() {
        // given
        given(confirmPaymentMapRepository.containsOrderId(anyString()))
                .willReturn(false);
        given(threadManager.sleep(anyLong()))
                .willReturn(false);
        // when
        // then
        assertThrows(
                ConfirmPaymentException.class,
                () -> service.confirmFinalApprovalOfCardPayment("user_id", "order_id")
        );
    }

    @Test
    @DisplayName("카드결제 최종 승인 중 최종승인 요청자와 주문자가 상이할 경우 예외가 발생한다")
    void confirmFinalApprovalOfCardPayment_when_forbidden() {
        // given
        var authId = "user_id";

        given(confirmPaymentMapRepository.containsOrderId(anyString()))
                .willReturn(true);

        var payment = Payment.cardOf();
        payment.changeStatusToPaid();
        payment.setInfo(CardPayment.CardPaymentInfo.of("impUid", "pgProvider", "pgTid",
                LocalDateTime.now(), "cardName", "cardNumber", 1));

        var order = MockFactory.createOrder("not_yours");
        order.setPayment(payment);
        order.calculateTotalValueAndSet();
        given(confirmPaymentMapRepository.getAndRemove(anyString()))
                .willReturn(order);
        // when
        // then
        assertThrows(
                NotYourOrderException.class,
                () -> service.confirmFinalApprovalOfCardPayment(authId, order.getId())
        );
        verify(confirmPaymentMapRepository, times(1)).put(anyString(), any());
    }

    @Test
    @DisplayName("카드결제 최종 승인 중 주문 정보가 DB에서 조회되지 않는 경우 예외가 발생한다")
    void confirmFinalApprovalOfCardPayment_when_notFoundOrder() {
        // given
        given(confirmPaymentMapRepository.containsOrderId(anyString()))
                .willReturn(true);

        var authId = "authId";

        var payment = Payment.cardOf();
        payment.changeStatusToPaid();
        payment.setInfo(CardPayment.CardPaymentInfo.of("impUid", "pgProvider", "pgTid",
                LocalDateTime.now(), "cardName", "cardNumber", 1));

        var order = MockFactory.createOrder(authId);
        order.setPayment(payment);
        order.calculateTotalValueAndSet();
        given(confirmPaymentMapRepository.getAndRemove(anyString()))
                .willReturn(order);

        given(ordersRepository.findById(anyString()))
                .willReturn(Optional.empty());

        // when
        // then
        assertThrows(
                InternalServerErrorException.class,
                () -> service.confirmFinalApprovalOfCardPayment(authId, order.getId())
        );
    }

    @Test
    @DisplayName("카드결제 최종 승인 중 결제금액과 주문금액이 상이하여 위조된 결제로 판단할 경우 예외가 발생한다")
    void confirmFinalApprovalOfCardPayment_when_forgeryPay() {
        // given
        given(confirmPaymentMapRepository.containsOrderId(anyString()))
                .willReturn(true);

        var authId = "authId";

        var payment = Payment.cardOf();
        payment.changeStatusToForgery();
        payment.setInfo(CardPayment.CardPaymentInfo.of("impUid", "pgProvider", "pgTid",
                LocalDateTime.now(), "cardName", "cardNumber", 1));

        var order = MockFactory.createOrder(authId);
        order.setPayment(payment);
        order.calculateTotalValueAndSet();
        given(confirmPaymentMapRepository.getAndRemove(anyString()))
                .willReturn(order);

        given(ordersRepository.findById(anyString()))
                .willReturn(Optional.of(order));
        // when
        // then
        assertThrows(
                RequestForgeryPayException.class,
                () -> service.confirmFinalApprovalOfCardPayment(authId, order.getId())
        );
    }

    @Test
    @DisplayName("카드결제 최종 승인 중 결제 상태가 PAID 또는 FORGERY가 아닌 경우 예외가 발생한다")
    void confirmFinalApprovalOfCardPayment_when_invaildPaymentStatus() {
        // given
        given(confirmPaymentMapRepository.containsOrderId(anyString()))
                .willReturn(true);

        var authId = "authId";

        var payment = Payment.cardOf();
        payment.changeStatusToRefund();
        payment.setInfo(CardPayment.CardPaymentInfo.of("impUid", "pgProvider", "pgTid",
                LocalDateTime.now(), "cardName", "cardNumber", 1));

        var order = MockFactory.createOrder(authId);
        order.setPayment(payment);
        order.calculateTotalValueAndSet();
        given(confirmPaymentMapRepository.getAndRemove(anyString()))
                .willReturn(order);

        given(ordersRepository.findById(anyString()))
                .willReturn(Optional.of(order));
        // when
        // then
        assertThrows(
                ConfirmPaymentException.class,
                () -> service.confirmFinalApprovalOfCardPayment(authId, order.getId())
        );
    }
}
