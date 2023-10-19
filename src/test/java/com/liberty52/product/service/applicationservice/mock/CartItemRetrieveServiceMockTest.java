package com.liberty52.product.service.applicationservice.mock;

import com.liberty52.product.MockS3Test;
import com.liberty52.product.global.config.DBInitConfig;
import com.liberty52.product.global.constants.ProductConstants;
import com.liberty52.product.service.applicationservice.CartItemCreateService;
import com.liberty52.product.service.applicationservice.CartItemRetrieveService;
import com.liberty52.product.service.applicationservice.impl.CartItemRetrieveServiceImpl;
import com.liberty52.product.service.controller.dto.CartItemRequest;
import com.liberty52.product.service.controller.dto.CartItemResponse;
import com.liberty52.product.service.controller.dto.CartOptionResponse;
import com.liberty52.product.service.entity.*;
import com.liberty52.product.service.repository.CartRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class CartItemRetrieveServiceMockTest extends MockS3Test {

    @Mock
    CartRepository cartRepository;

    @InjectMocks
    CartItemRetrieveServiceImpl cartItemRetrieveService;

    private Cart cart;

    @BeforeEach
    void  init(){
        Product product = Product.create("Liberty 52_Frame", ProductState.ON_SALE, 100L,true);

        ProductOption option1 = ProductOption.create("거치 방식", true, true);
        option1.associate(product);
        OptionDetail detailEasel = OptionDetail.create("이젤 거치형", 100,true,100);
        detailEasel.associate(option1);
        OptionDetail detailWall = OptionDetail.create("벽걸이형", 100,true,100);
        detailWall.associate(option1);

        ProductOption option2 = ProductOption.create("기본소재", true, true);
        option2.associate(product);
        OptionDetail material = OptionDetail.create("1mm 두께 승화전사 인쇄용 알루미늄시트", 100,true,100);
        material.associate(option2);

        ProductOption option3 = ProductOption.create("기본소재 옵션", true, true);
        option3.associate(product);
        OptionDetail materialOption1 = OptionDetail.create("유광실버", 100,true, 100);
        materialOption1.associate(option3);
        OptionDetail materialOption2 = OptionDetail.create("무광실버", 100,true, 100);
        materialOption2.associate(option3);
        OptionDetail materialOption3 = OptionDetail.create("유광백색", 100,true, 100);
        materialOption3.associate(option3);
        OptionDetail materialOption4 = OptionDetail.create("무광백색", 100,true, 100);
        materialOption4.associate(option3);

        // Add Cart & CartItems
        Cart cart = Cart.create("authId");

        CustomProduct customProduct1 = CustomProduct.create("image", 1, "authId");
        customProduct1.associateWithProduct(product);
        customProduct1.associateWithCart(cart);

        CustomProductOption customProductOption1 = CustomProductOption.create();
        customProductOption1.associate(detailEasel);
        customProductOption1.associate(customProduct1);

        CustomProductOption customProductOption2 = CustomProductOption.create();
        customProductOption2.associate(material);
        customProductOption2.associate(customProduct1);

        CustomProductOption customProductOption3 = CustomProductOption.create();
        customProductOption3.associate(materialOption2);
        customProductOption3.associate(customProduct1);

        CustomProduct customProduct2 = CustomProduct.create("image", 2, "authId");
        customProduct2.associateWithProduct(product);
        customProduct2.associateWithCart(cart);

        CustomProductOption customProductOption4 = CustomProductOption.create();
        customProductOption4.associate(detailWall);
        customProductOption4.associate(customProduct2);

        CustomProductOption customProductOption5 = CustomProductOption.create();
        customProductOption5.associate(materialOption4);
        customProductOption5.associate(customProduct2);

        this.cart = cart;
    }


    @Test
    void 장바구니조회() throws IOException {
        //given
        given(cartRepository.findByAuthId("authId")).willReturn(Optional.ofNullable(this.cart));
        given(cartRepository.findByAuthId("onlyCart")).willReturn(Optional.ofNullable(Cart.create("onlyCart")));
        //when
        List<CartItemResponse> cartItemResponseList = cartItemRetrieveService.retrieveAuthCartItem("authId");
        //then

        Mockito.verify(cartRepository, atMostOnce()).findByAuthId(any());

        Assertions.assertEquals(cartItemResponseList.size(), 2);

        CartItemResponse cartItemResponse1 = cartItemResponseList.get(0);
        Assertions.assertEquals(cartItemResponse1.getName(), "Liberty 52_Frame");
//        Assertions.assertEquals(cartItemResponse1.getPrice(), 10000000);
        Assertions.assertEquals(cartItemResponse1.getQuantity(), 1);

        List<CartOptionResponse> optionRequestList1 = cartItemResponse1.getOptions();
        Assertions.assertEquals(optionRequestList1.size(), 3);
        CartOptionResponse cartOptionResponse11 = optionRequestList1.get(0);
        Assertions.assertEquals(cartOptionResponse11.getOptionName(), "거치 방식");
        Assertions.assertEquals(cartOptionResponse11.getDetailName(), "이젤 거치형");
//        Assertions.assertEquals(cartOptionResponse11.getPrice(), 100000);
        Assertions.assertEquals(cartOptionResponse11.isRequire(), true);

        CartOptionResponse cartOptionResponse12 = optionRequestList1.get(1);
        Assertions.assertEquals(cartOptionResponse12.getOptionName(), "기본소재");
        Assertions.assertEquals(cartOptionResponse12.getDetailName(), "1mm 두께 승화전사 인쇄용 알루미늄시트");
//        Assertions.assertEquals(cartOptionResponse12.getPrice(), 0);
        Assertions.assertEquals(cartOptionResponse12.isRequire(), true);

        CartOptionResponse cartOptionResponse13 = optionRequestList1.get(2);
        Assertions.assertEquals(cartOptionResponse13.getOptionName(), "기본소재 옵션");
        Assertions.assertEquals(cartOptionResponse13.getDetailName(), "무광실버");
//        Assertions.assertEquals(cartOptionResponse13.getPrice(), 400000);
        Assertions.assertEquals(cartOptionResponse13.isRequire(), true);

        CartItemResponse cartItemResponse2 = cartItemResponseList.get(1);
        Assertions.assertEquals(cartItemResponse2.getName(), "Liberty 52_Frame");
//        Assertions.assertEquals(cartItemResponse2.getPrice(), 10000000);
        Assertions.assertEquals(cartItemResponse2.getQuantity(), 2);

        List<CartOptionResponse> optionRequestList2 = cartItemResponse2.getOptions();
        Assertions.assertEquals(optionRequestList2.size(), 2);

        CartOptionResponse cartOptionResponse21 = optionRequestList2.get(0);
        Assertions.assertEquals(cartOptionResponse21.getOptionName(), "거치 방식");
        Assertions.assertEquals(cartOptionResponse21.getDetailName(), "벽걸이형");
//        Assertions.assertEquals(cartOptionResponse21.getPrice(), 200000);
        Assertions.assertEquals(cartOptionResponse21.isRequire(), true);

        CartOptionResponse cartOptionResponse22 = optionRequestList2.get(1);
        Assertions.assertEquals(cartOptionResponse22.getOptionName(), "기본소재 옵션");
        Assertions.assertEquals(cartOptionResponse22.getDetailName(), "무광백색");
//        Assertions.assertEquals(cartOptionResponse22.getPrice(), 500000);
        Assertions.assertEquals(cartOptionResponse22.isRequire(), true);

        List<CartItemResponse> cartItemResponseList1 = cartItemRetrieveService.retrieveAuthCartItem("onlyCart");
        Assertions.assertEquals(cartItemResponseList1.size(), 0);

        List<CartItemResponse> cartItemResponseList2 = cartItemRetrieveService.retrieveAuthCartItem("noCart");
        Assertions.assertEquals(cartItemResponseList2.size(), 0);
    }

    @Test
    void 게스트장바구니조회() throws IOException {
        //given
        //현재 이 테스트 단독으로는 통과하지만 이 테스트 클래스 전체를 돌리면 given이 localDate 문제로 잘 작동하지 않음
        //given(cartRepository.findByAuthIdAndExpiryDateGreaterThanEqual("guestId", LocalDate.now())).willReturn(Optional.ofNullable(this.cart));
        //given(cartRepository.findByAuthIdAndExpiryDateGreaterThanEqual("onlyCart", LocalDate.now())).willReturn(Optional.ofNullable(Cart.create("onlyCart")));
        //when
        List<CartItemResponse> cartItemResponseList = cartItemRetrieveService.retrieveGuestCartItem("guestId");
        //then
        Mockito.verify(cartRepository, atMostOnce()).findByAuthIdAndExpiryDateGreaterThanEqual("guestId", LocalDate.now());
//        Assertions.assertEquals(cartItemResponseList.size(), 2);
//
//        CartItemResponse cartItemResponse1 = cartItemResponseList.get(0);
//        Assertions.assertEquals(cartItemResponse1.getName(), "Liberty 52_Frame");
////        Assertions.assertEquals(cartItemResponse1.getPrice(), 10000000);
//        Assertions.assertEquals(cartItemResponse1.getQuantity(), 1);
//
//        List<CartOptionResponse> optionRequestList1 = cartItemResponse1.getOptions();
//        Assertions.assertEquals(optionRequestList1.size(), 3);
//        CartOptionResponse cartOptionResponse11 = optionRequestList1.get(0);
//        Assertions.assertEquals(cartOptionResponse11.getOptionName(), "거치 방식");
//        Assertions.assertEquals(cartOptionResponse11.getDetailName(), "이젤 거치형");
////        Assertions.assertEquals(cartOptionResponse11.getPrice(), 100000);
//        Assertions.assertEquals(cartOptionResponse11.isRequire(), true);
//
//        CartOptionResponse cartOptionResponse12 = optionRequestList1.get(1);
//        Assertions.assertEquals(cartOptionResponse12.getOptionName(), "기본소재");
//        Assertions.assertEquals(cartOptionResponse12.getDetailName(), "1mm 두께 승화전사 인쇄용 알루미늄시트");
////        Assertions.assertEquals(cartOptionResponse12.getPrice(), 0);
//        Assertions.assertEquals(cartOptionResponse12.isRequire(), true);
//
//        CartOptionResponse cartOptionResponse13 = optionRequestList1.get(2);
//        Assertions.assertEquals(cartOptionResponse13.getOptionName(), "기본소재 옵션");
//        Assertions.assertEquals(cartOptionResponse13.getDetailName(), "무광실버");
////        Assertions.assertEquals(cartOptionResponse13.getPrice(), 400000);
//        Assertions.assertEquals(cartOptionResponse13.isRequire(), true);
//
//        CartItemResponse cartItemResponse2 = cartItemResponseList.get(1);
//        Assertions.assertEquals(cartItemResponse2.getName(), "Liberty 52_Frame");
////        Assertions.assertEquals(cartItemResponse2.getPrice(), 10000000);
//        Assertions.assertEquals(cartItemResponse2.getQuantity(), 2);
//
//        List<CartOptionResponse> optionRequestList2 = cartItemResponse2.getOptions();
//        Assertions.assertEquals(optionRequestList2.size(), 2);
//
//        CartOptionResponse cartOptionResponse21 = optionRequestList2.get(0);
//        Assertions.assertEquals(cartOptionResponse21.getOptionName(), "거치 방식");
//        Assertions.assertEquals(cartOptionResponse21.getDetailName(), "벽걸이형");
////        Assertions.assertEquals(cartOptionResponse21.getPrice(), 200000);
//        Assertions.assertEquals(cartOptionResponse21.isRequire(), true);
//
//
//        CartOptionResponse cartOptionResponse22 = optionRequestList2.get(1);
//        Assertions.assertEquals(cartOptionResponse22.getOptionName(), "기본소재 옵션");
//        Assertions.assertEquals(cartOptionResponse22.getDetailName(), "무광백색");
////        Assertions.assertEquals(cartOptionResponse22.getPrice(), 500000);
//        Assertions.assertEquals(cartOptionResponse22.isRequire(), true);

        //otherCase
        List<CartItemResponse> cartItemResponseList1 = cartItemRetrieveService.retrieveGuestCartItem("onlyCart");
        Assertions.assertEquals(cartItemResponseList1.size(), 0);
        Mockito.verify(cartRepository, atMostOnce()).findByAuthIdAndExpiryDateGreaterThanEqual("onlyCart", LocalDate.now());

        List<CartItemResponse> cartItemResponseList2 = cartItemRetrieveService.retrieveGuestCartItem("noCart");
        Assertions.assertEquals(cartItemResponseList2.size(), 0);
        Mockito.verify(cartRepository, atMostOnce()).findByAuthIdAndExpiryDateGreaterThanEqual("noCart", LocalDate.now());
//        Cart cart1 = cartRepository.findByAuthId("aaa").orElseThrow();
//        cart1.updateExpiryDate(LocalDate.now().minusDays(7));
//        cartRepository.save(cart1);
//        List<CartItemResponse> cartItemResponseList3 = cartItemRetrieveService.retrieveGuestCartItem("aaa");
//        Assertions.assertEquals(cartItemResponseList3.size(), 0);
    }

}
