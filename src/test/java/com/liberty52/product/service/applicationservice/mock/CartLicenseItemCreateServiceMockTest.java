package com.liberty52.product.service.applicationservice.mock;

import com.liberty52.product.global.exception.external.notfound.OptionDetailNotFoundByNameException;
import com.liberty52.product.global.exception.external.notfound.ProductNotFoundByNameException;
import com.liberty52.product.service.applicationservice.impl.CartItemCreateServiceImpl;
import com.liberty52.product.service.controller.dto.CartItemRequest;
import com.liberty52.product.service.entity.Cart;
import com.liberty52.product.service.entity.OptionDetail;
import com.liberty52.product.service.entity.Product;
import com.liberty52.product.service.entity.ProductState;
import com.liberty52.product.service.entity.license.LicenseOptionDetail;
import com.liberty52.product.service.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CartLicenseItemCreateServiceMockTest {
    @InjectMocks
    CartItemCreateServiceImpl cartItemCreateService;

    @Mock
    CartRepository cartRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    OptionDetailRepository optionDetailRepository;

    @Mock
    CustomProductRepository customProductRepository;

    @Mock
    CustomProductOptionRepository customProductOptionRepository;

    @Mock
    CustomLicenseOptionRepository customLicenseOptionRepository;

    @Mock
    LicenseOptionDetailRepository licenseOptionDetailRepository;

    @BeforeEach
    void  init() {

    }
    @Test
    void 라이선스상품장바구니생성() throws IOException {

        //given
        String[] option = {"licenseOption","OPT-001", "OPT-003", "OPT-005"};
        CartItemRequest dto1 = new CartItemRequest().builder().productId("LIB-002").quantity(1).optionDetailIds(option).build();
        CartItemRequest dto2 = new CartItemRequest().builder().productId("L").quantity(2).optionDetailIds(option).build();
        String[] optionErr = {"err","OPT-002", "OPT-003", "OPT-005"};
        CartItemRequest dto3 = new CartItemRequest().builder().productId("LIB-002").quantity(4).optionDetailIds(optionErr).build();

        MockMultipartFile imageFile = new MockMultipartFile("image", "test.png", "image/jpeg", new FileInputStream("src/test/resources/static/test.jpg"));

        given(cartRepository.save(any())).willReturn(Cart.create("testId"));

        given(productRepository.findById("LIB-002")).willReturn(Optional.ofNullable(Product.create("Liberty 52_Frame", ProductState.ON_SALE,100L,false)));

        given(customLicenseOptionRepository.save(any())).willReturn(null);
        given(licenseOptionDetailRepository.findById("licenseOption")).willReturn(Optional.ofNullable(LicenseOptionDetail.create("그림명", "작가", 100, true, "url", 100, LocalDate.now(), LocalDate.now())));


        given(customProductRepository.save(any())).willReturn(null);
        given(customProductOptionRepository.save(any())).willReturn(null);

        given(optionDetailRepository.findById("OPT-001")).willReturn(Optional.ofNullable(OptionDetail.create("이젤 거치형", 100, true, 100)));
        given(optionDetailRepository.findById("OPT-003")).willReturn(Optional.ofNullable(OptionDetail.create("1mm 두께 승화전사 인쇄용 알루미늄시트", 100, true, 100)));
        given(optionDetailRepository.findById("OPT-005")).willReturn(Optional.ofNullable(OptionDetail.create("무광실버", 100, true, 100)));

        //when
        cartItemCreateService.createAuthCartItem("testId", imageFile, dto1);

        //then
        verify(cartRepository, atMostOnce()).findByAuthId(any());
        verify(licenseOptionDetailRepository, atMostOnce()).findById(any());
        //verify(optionDetailRepository, times(3)).findById(any());

        //예외
        Assertions.assertThrows(ProductNotFoundByNameException.class, () -> cartItemCreateService.createAuthCartItem("testId", imageFile, dto2));
//
        Assertions.assertThrows(OptionDetailNotFoundByNameException.class, () -> cartItemCreateService.createAuthCartItem("testId", imageFile, dto3));



    }

}
