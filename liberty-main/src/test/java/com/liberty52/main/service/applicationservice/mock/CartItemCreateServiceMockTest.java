package com.liberty52.main.service.applicationservice.mock;

import com.liberty52.main.MockS3Test;
import com.liberty52.main.global.exception.external.notfound.OptionDetailNotFoundByNameException;
import com.liberty52.main.global.exception.external.notfound.ProductNotFoundByNameException;
import com.liberty52.main.service.applicationservice.impl.CartItemCreateServiceImpl;
import com.liberty52.main.service.controller.dto.CartItemRequest;
import com.liberty52.main.service.entity.Cart;
import com.liberty52.main.service.entity.OptionDetail;
import com.liberty52.main.service.entity.Product;
import com.liberty52.main.service.entity.ProductState;
import com.liberty52.main.service.repository.*;
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
    void init() {

    }

    @Test
    void 장바구니생성() throws IOException {

        //given
        String[] option = {"OPT-001", "OPT-003", "OPT-005"};
        CartItemRequest dto1 = new CartItemRequest().builder().productId("LIB-001").quantity(1).optionDetailIds(option).build();
        CartItemRequest dto2 = new CartItemRequest().builder().productId("L").quantity(2).optionDetailIds(option).build();
        String[] optionErr = {"OPT-002", "OPT-003", "err"};
        CartItemRequest dto3 = new CartItemRequest().builder().productId("LIB-001").quantity(4).optionDetailIds(optionErr).build();

        MockMultipartFile imageFile = new MockMultipartFile("image", "test.png", "image/jpeg", new FileInputStream("src/test/resources/static/test.jpg"));
        given(productRepository.findById("LIB-001")).willReturn(Optional.ofNullable(Product.create("Liberty 52_Frame",ProductState.ON_SALE,100L,true)));
        given(optionDetailRepository.findById("OPT-002")).willReturn(Optional.ofNullable(OptionDetail.create("벽걸이형", 100, true, 100)));

        given(cartRepository.save(any())).willReturn(Cart.create("testId"));

        given(productRepository.findById("LIB-001")).willReturn(Optional.ofNullable(Product.create("Liberty 52_Frame", ProductState.ON_SALE, 100L, true)));

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
        //verify(optionDetailRepository, times(3)).findById(any());

        //예외
        Assertions.assertThrows(ProductNotFoundByNameException.class, () -> cartItemCreateService.createAuthCartItem("testId", imageFile, dto2));

        Assertions.assertThrows(OptionDetailNotFoundByNameException.class, () -> cartItemCreateService.createAuthCartItem("testId", imageFile, dto3));


    }

    @Test
    void 게스트장바구니생성() throws IOException {
        //given
        String[] option = {"OPT-001", "OPT-003", "OPT-005"};
        CartItemRequest dto1 = new CartItemRequest().builder().productId("LIB-001").quantity(1).optionDetailIds(option).build();
        CartItemRequest dto2 = new CartItemRequest().builder().productId("L").quantity(2).optionDetailIds(option).build();
        String[] optionErr = {"OPT-002", "OPT-003", "err"};
        CartItemRequest dto3 = new CartItemRequest().builder().productId("LIB-001").quantity(4).optionDetailIds(optionErr).build();

        MockMultipartFile imageFile = new MockMultipartFile("image", "test.png", "image/jpeg", new FileInputStream("src/test/resources/static/test.jpg"));

//        given(cartRepository.save(any())).willReturn(Cart.create("testId"));
//
//        given(productRepository.findById("LIB-001")).willReturn(Optional.ofNullable(Product.create("Liberty 52_Frame",ProductState.ON_SALE,100L)));
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
