package com.liberty52.main.service.applicationservice;

import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.service.controller.dto.*;
import com.liberty52.main.service.entity.*;
import com.liberty52.main.service.repository.CartRepository;
import com.liberty52.main.service.repository.CustomProductRepository;
import com.liberty52.main.service.repository.ProductRepository;
import com.liberty52.main.service.repository.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static com.liberty52.main.global.constants.RoleConstants.ADMIN;

@SpringBootTest
@Transactional
public class ProductInfoRetrieveServiceTest {

    @Autowired
    ProductInfoRetrieveService productInfoRetrieveService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CustomProductRepository customProductRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @BeforeEach
    void beforeEach() {

    }

    @Test
    void 모든판매중인상품조회() {
        Pageable pageable = PageRequest.of(0, 10);
        ProductListResponseDto productListResponseDtoList = productInfoRetrieveService.retrieveProductList(pageable);
        System.out.println(productListResponseDtoList.getTotalCount());
        System.out.println(productListResponseDtoList.getContents().size());
        ProductListResponseDto.ProductInfo testProduct = productListResponseDtoList.getContents().get(0);
        Assertions.assertEquals(testProduct.getId(), "LIB-001");
        Assertions.assertNull(testProduct.getPictureUrl());
        Assertions.assertEquals(testProduct.getName(), "Liberty 52_Frame");
        Assertions.assertTrue(testProduct.getPrice() >= 0);
        Assertions.assertTrue(testProduct.getMeanRate() >= 0);
        Assertions.assertEquals(testProduct.getState(), ProductState.ON_SALE.toString());
        Assertions.assertTrue(testProduct.isCustom());
    }

    @Test
    void 상품옵션조회() {
        String role = "ADMIN";
        Product product = productRepository.findById("LIB-001").orElse(null);
        boolean onSale = false;

        List<ProductOptionResponseDto> productOptionResponseDtoList = productInfoRetrieveService.retrieveProductOptionInfoListByAdmin(role, "LIB-001", onSale);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productInfoRetrieveService.retrieveProductOptionInfoListByAdmin(role, "null", onSale));

        Assertions.assertEquals(productOptionResponseDtoList.size(), product.getProductOptions().size());
        ProductOptionResponseDto optionDto = productOptionResponseDtoList.get(0);
        ProductOption productOption = product.getProductOptions().get(0);

        Assertions.assertNotNull(product.getDeliveryOption().getCourierName());
        Assertions.assertNotNull(product.getDeliveryOption().getFee());

        Assertions.assertEquals(optionDto.getOptionName(), productOption.getName());
        Assertions.assertEquals(optionDto.isOnSale(), productOption.isOnSale());
        Assertions.assertEquals(optionDto.isRequire(), productOption.isRequire());

        Assertions.assertEquals(optionDto.getOptionDetailList().size(), productOption.getOptionDetails().size());
        ProductOptionDetailResponseDto detailDto = optionDto.getOptionDetailList().get(0);
        OptionDetail optionDetail = productOption.getOptionDetails().get(0);

        Assertions.assertEquals(detailDto.getOptionDetailName(), optionDetail.getName());
        Assertions.assertEquals(detailDto.isOnSale(), optionDetail.isOnSale());
        Assertions.assertEquals(detailDto.getPrice(), optionDetail.getPrice());
        Assertions.assertEquals(detailDto.getStock(), optionDetail.getStock());
    }

    @Test
    void 상품조회() {
        List<ProductInfoRetrieveResponseDto> dtoList = productInfoRetrieveService.retrieveProductListByAdmin(ADMIN);


        Product product = productRepository.findByName("Liberty 52_Frame").orElseGet(null);
        List<Review> reviewList = reviewRepository.findByCustomProduct_Product(product);
        int sum = 0;
        for (Review review : reviewList) {
            sum = sum + review.getRating();
        }

        Assertions.assertEquals(dtoList.size(), 1);
        ProductInfoRetrieveResponseDto dto = dtoList.get(0);
        Assertions.assertEquals(dto.getId(), "LIB-001");
        Assertions.assertNull(dto.getPictureUrl());
        Assertions.assertEquals(dto.getContent(), "");
        Assertions.assertEquals(dto.getName(), "Liberty 52_Frame");
        Assertions.assertEquals(dto.getPrice(), 100);
        Assertions.assertEquals(dto.getMeanRating(), sum / reviewList.size());
        Assertions.assertEquals(dto.getRatingCount(), reviewList.size());
        Assertions.assertEquals(dto.getState(), ProductState.ON_SALE);
        Assertions.assertTrue(dto.isCustom());
    }

    @Test
    void 단일상품조회() {
        Product product = productRepository.findByName("Liberty 52_Frame").orElseGet(null);
        List<Review> reviewList = reviewRepository.findByCustomProduct_Product(product);
        int sum = 0;
        for (Review review : reviewList) {
            sum = sum + review.getRating();
        }
        ProductInfoRetrieveResponseDto dto = productInfoRetrieveService.retrieveProductByAdmin(ADMIN, product.getId());
        Assertions.assertEquals(dto.getId(), "LIB-001");
        Assertions.assertNull(dto.getPictureUrl());
        Assertions.assertEquals(dto.getContent(), "");
        Assertions.assertEquals(dto.getName(), "Liberty 52_Frame");
        Assertions.assertEquals(dto.getPrice(), 100);
        Assertions.assertEquals(dto.getMeanRating(), sum / reviewList.size());
        Assertions.assertEquals(dto.getRatingCount(), reviewList.size());
        Assertions.assertEquals(dto.getState(), ProductState.ON_SALE);
        Assertions.assertTrue(dto.isCustom());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productInfoRetrieveService.retrieveProductByAdmin(ADMIN, "null"));

    }

    @Test
    void 장바구니상품정보조회() throws IOException {
        String authId = "cartTester";
        int quantity = 2;
        String testUrl = "test";
        Product product = productRepository.findByName("Liberty 52_Frame").orElseGet(null);

        Cart cart = Cart.create(authId);
        cartRepository.save(cart);
        CustomProduct customProduct = CustomProduct.createCartItem(authId, quantity, testUrl);
        customProduct.associateWithCart(cart);
        customProduct.associateWithProduct(product);
        customProductRepository.save(customProduct);
        cartRepository.save(cart);

        List<ProductInfoByCartResponseDto> testList = productInfoRetrieveService.retrieveProductOptionListByCart(authId);
        Assertions.assertEquals(testList.get(0).getProductId(), product.getId());

        List<ProductOption> optionList = product.getProductOptions();
        List<ProductOptionInfoByCartResponseDto> optionTestList = testList.get(0).getProductOptionList();
        for (int i = 0; i < product.getProductOptions().size(); i++) {
            Assertions.assertEquals(optionTestList.get(i).getOptionId(), optionList.get(i).getId());
            Assertions.assertEquals(optionTestList.get(i).getOptionName(), optionList.get(i).getName());
            Assertions.assertEquals(optionTestList.get(i).isRequire(), optionList.get(i).isRequire());

            List<OptionDetail> detailList = optionList.get(i).getOptionDetails();
            List<OptionDetailInfoByCartResponseDto> detailTestList = optionTestList.get(i).getOptionDetailList();
            for (int k = 0; k < detailTestList.size(); k++) {
                Assertions.assertEquals(detailTestList.get(k).getOptionDetailId(), detailList.get(k).getId());
                Assertions.assertEquals(detailTestList.get(k).getOptionDetailName(), detailList.get(k).getName());
                Assertions.assertEquals(detailTestList.get(k).getPrice(), detailList.get(k).getPrice());

            }
        }


    }
}
