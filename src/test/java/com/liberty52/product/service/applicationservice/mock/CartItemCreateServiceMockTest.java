package com.liberty52.product.service.applicationservice.mock;

import com.liberty52.product.MockS3Test;
import com.liberty52.product.global.adapter.s3.S3UploaderApi;
import com.liberty52.product.global.exception.external.notfound.OptionDetailNotFoundByNameException;
import com.liberty52.product.global.exception.external.notfound.ProductNotFoundByNameException;
import com.liberty52.product.service.applicationservice.CartItemCreateService;
import com.liberty52.product.service.applicationservice.impl.CartItemCreateServiceImpl;
import com.liberty52.product.service.controller.dto.CartItemRequest;
import com.liberty52.product.service.entity.*;
import com.liberty52.product.service.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartItemCreateServiceMockTest extends MockS3Test {

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

    @BeforeEach
    void  init() {

    }

    @Test
    void 장바구니생성() throws IOException {

        //given
        String[] option = {"OPT-001", "OPT-003", "OPT-005"};
        CartItemRequest dto1 = new CartItemRequest().builder().productName("Liberty 52_Frame").quantity(1).optionDetailIds(option).build();
        CartItemRequest dto2 = new CartItemRequest().builder().productName("L").quantity(2).optionDetailIds(option).build();
        String[] optionErr = {"OPT-002", "OPT-003", "err"};
        CartItemRequest dto3 = new CartItemRequest().builder().productName("Liberty 52_Frame").quantity(4).optionDetailIds(optionErr).build();

        MockMultipartFile imageFile = new MockMultipartFile("image", "test.png", "image/jpeg", new FileInputStream("src/test/resources/static/test.jpg"));

        given(cartRepository.save(any())).willReturn(Cart.create("testId"));

        given(productRepository.findByName("Liberty 52_Frame")).willReturn(Optional.ofNullable(Product.create("Liberty 52_Frame",ProductState.ON_SALE,100L)));

        given(customProductRepository.save(any())).willReturn(null);
        given(customProductOptionRepository.save(any())).willReturn(null);

        given(optionDetailRepository.findById("OPT-001")).willReturn(Optional.ofNullable(OptionDetail.create("이젤 거치형", 100, true, 100)));
        given(optionDetailRepository.findById("OPT-003")).willReturn(Optional.ofNullable(OptionDetail.create("1mm 두께 승화전사 인쇄용 알루미늄시트", 100, true, 100)));
        given(optionDetailRepository.findById("OPT-005")).willReturn(Optional.ofNullable(OptionDetail.create("무광실버", 100, true, 100)));
        given(optionDetailRepository.findById("OPT-002")).willReturn(Optional.ofNullable(OptionDetail.create("벽걸이형", 100, true, 100)));

        //when
        cartItemCreateService.createAuthCartItem("testId", imageFile, dto1);

        //then
        verify(cartRepository, atMostOnce()).findByAuthId(any());
        verify(optionDetailRepository, times(3)).findById(any());

        //예외
        Assertions.assertThrows(ProductNotFoundByNameException.class, () -> cartItemCreateService.createAuthCartItem("testId", imageFile, dto2));

        Assertions.assertThrows(OptionDetailNotFoundByNameException.class, () -> cartItemCreateService.createAuthCartItem("testId", imageFile, dto3));



    }

    @Test
    void 게스트장바구니생성() throws IOException {
        //given
        String[] option = {"OPT-001", "OPT-003", "OPT-005"};
        CartItemRequest dto1 = new CartItemRequest().builder().productName("Liberty 52_Frame").quantity(1).optionDetailIds(option).build();
        CartItemRequest dto2 = new CartItemRequest().builder().productName("L").quantity(2).optionDetailIds(option).build();
        String[] optionErr = {"OPT-002", "OPT-003", "err"};
        CartItemRequest dto3 = new CartItemRequest().builder().productName("Liberty 52_Frame").quantity(4).optionDetailIds(optionErr).build();

        MockMultipartFile imageFile = new MockMultipartFile("image", "test.png", "image/jpeg", new FileInputStream("src/test/resources/static/test.jpg"));

//        given(cartRepository.save(any())).willReturn(Cart.create("testId"));
//
//        given(productRepository.findByName("Liberty 52_Frame")).willReturn(Optional.ofNullable(Product.create("Liberty 52_Frame",ProductState.ON_SALE,100L)));
//
//        given(customProductRepository.save(any())).willReturn(null);
//        given(customProductOptionRepository.save(any())).willReturn(null);
//
//        given(optionDetailRepository.findById("OPT-001")).willReturn(Optional.ofNullable(OptionDetail.create("이젤 거치형", 100, true, 100)));
//        given(optionDetailRepository.findById("OPT-003")).willReturn(Optional.ofNullable(OptionDetail.create("1mm 두께 승화전사 인쇄용 알루미늄시트", 100, true, 100)));
//        given(optionDetailRepository.findById("OPT-005")).willReturn(Optional.ofNullable(OptionDetail.create("무광실버", 100, true, 100)));
//        given(optionDetailRepository.findById("OPT-002")).willReturn(Optional.ofNullable(OptionDetail.create("벽걸이형", 100, true, 100)));


        //when
        cartItemCreateService.createGuestCartItem("guest", imageFile, dto1);


        //then
        verify(cartRepository, atMostOnce()).findByAuthIdAndExpiryDateGreaterThanEqual(any(), any());
        //Mockito.verify(optionDetailRepository, times(3)).findById(any());

        //exception
        Assertions.assertThrows(ProductNotFoundByNameException.class, () -> cartItemCreateService.createAuthCartItem("testId", imageFile, dto2));

        Assertions.assertThrows(OptionDetailNotFoundByNameException.class, () -> cartItemCreateService.createAuthCartItem("authId", imageFile, dto3));



    }
}
