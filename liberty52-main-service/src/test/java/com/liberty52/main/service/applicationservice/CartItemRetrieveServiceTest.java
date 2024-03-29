package com.liberty52.main.service.applicationservice;

import com.liberty52.main.MockS3Test;
import com.liberty52.main.service.controller.dto.CartItemRequest;
import com.liberty52.main.service.controller.dto.CartItemResponse;
import com.liberty52.main.service.controller.dto.CartOptionResponse;
import com.liberty52.main.service.entity.Cart;
import com.liberty52.main.service.repository.CartRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
public class CartItemRetrieveServiceTest extends MockS3Test {

    @Autowired
    CartItemCreateService cartItemCreateService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRetrieveService cartItemRetrieveService;

    @Test
    void 장바구니조회() throws IOException {
        MockMultipartFile imageFile = new MockMultipartFile("image", "test.png", "image/jpeg", new FileInputStream("src/test/resources/static/test.jpg"));

        Cart cart = Cart.create("ccc");
        cartRepository.save(cart);

        CartItemRequest dto1 = new CartItemRequest();
        String[] option = {"OPT-001", "OPT-003", "OPT-005"};
        dto1.create("LIB-001", 1, option);
        cartItemCreateService.createAuthCartItem("aaa", imageFile, dto1);


        CartItemRequest dto2 = new CartItemRequest();
        String[] option2 = {"OPT-002", "OPT-007"};

        dto2.create("LIB-001", 2, option2);
        cartItemCreateService.createAuthCartItem("aaa", imageFile, dto2);

        List<CartItemResponse> cartItemResponseList = cartItemRetrieveService.retrieveAuthCartItem("aaa");
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
        Assertions.assertTrue(cartOptionResponse11.isRequire());

        CartOptionResponse cartOptionResponse12 = optionRequestList1.get(1);
        Assertions.assertEquals(cartOptionResponse12.getOptionName(), "기본소재");
        Assertions.assertEquals(cartOptionResponse12.getDetailName(), "1mm 두께 승화전사 인쇄용 알루미늄시트");
//        Assertions.assertEquals(cartOptionResponse12.getPrice(), 0);
        Assertions.assertTrue(cartOptionResponse12.isRequire());


        CartOptionResponse cartOptionResponse13 = optionRequestList1.get(2);
        Assertions.assertEquals(cartOptionResponse13.getOptionName(), "기본소재 옵션");
        Assertions.assertEquals(cartOptionResponse13.getDetailName(), "무광실버");
//        Assertions.assertEquals(cartOptionResponse13.getPrice(), 400000);
        Assertions.assertTrue(cartOptionResponse13.isRequire());


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
        Assertions.assertTrue(cartOptionResponse21.isRequire());


        CartOptionResponse cartOptionResponse22 = optionRequestList2.get(1);
        Assertions.assertEquals(cartOptionResponse22.getOptionName(), "기본소재 옵션");
        Assertions.assertEquals(cartOptionResponse22.getDetailName(), "무광백색");
//        Assertions.assertEquals(cartOptionResponse22.getPrice(), 500000);
        Assertions.assertTrue(cartOptionResponse22.isRequire());

        List<CartItemResponse> cartItemResponseList1 = cartItemRetrieveService.retrieveAuthCartItem("bbb");
        Assertions.assertEquals(cartItemResponseList1.size(), 0);

        List<CartItemResponse> cartItemResponseList2 = cartItemRetrieveService.retrieveAuthCartItem("ccc");
        Assertions.assertEquals(cartItemResponseList2.size(), 0);
    }

    @Test
    void 게스트장바구니조회() throws IOException {
        MockMultipartFile imageFile = new MockMultipartFile("image", "test.png", "image/jpeg", new FileInputStream("src/test/resources/static/test.jpg"));

        Cart cart = Cart.create("ccc");
        cartRepository.save(cart);

        CartItemRequest dto1 = new CartItemRequest();
        String[] option = {"OPT-001", "OPT-003", "OPT-005"};
        dto1.create("LIB-001", 1, option);
        cartItemCreateService.createGuestCartItem("aaa", imageFile, dto1);


        CartItemRequest dto2 = new CartItemRequest();
        String[] option2 = {"OPT-002", "OPT-007"};

        dto2.create("LIB-001", 2, option2);
        cartItemCreateService.createGuestCartItem("aaa", imageFile, dto2);


        List<CartItemResponse> cartItemResponseList = cartItemRetrieveService.retrieveGuestCartItem("aaa");
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
        Assertions.assertTrue(cartOptionResponse11.isRequire());

        CartOptionResponse cartOptionResponse12 = optionRequestList1.get(1);
        Assertions.assertEquals(cartOptionResponse12.getOptionName(), "기본소재");
        Assertions.assertEquals(cartOptionResponse12.getDetailName(), "1mm 두께 승화전사 인쇄용 알루미늄시트");
//        Assertions.assertEquals(cartOptionResponse12.getPrice(), 0);
        Assertions.assertTrue(cartOptionResponse12.isRequire());


        CartOptionResponse cartOptionResponse13 = optionRequestList1.get(2);
        Assertions.assertEquals(cartOptionResponse13.getOptionName(), "기본소재 옵션");
        Assertions.assertEquals(cartOptionResponse13.getDetailName(), "무광실버");
//        Assertions.assertEquals(cartOptionResponse13.getPrice(), 400000);
        Assertions.assertTrue(cartOptionResponse13.isRequire());


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
        Assertions.assertTrue(cartOptionResponse21.isRequire());


        CartOptionResponse cartOptionResponse22 = optionRequestList2.get(1);
        Assertions.assertEquals(cartOptionResponse22.getOptionName(), "기본소재 옵션");
        Assertions.assertEquals(cartOptionResponse22.getDetailName(), "무광백색");
//        Assertions.assertEquals(cartOptionResponse22.getPrice(), 500000);
        Assertions.assertTrue(cartOptionResponse22.isRequire());

        List<CartItemResponse> cartItemResponseList1 = cartItemRetrieveService.retrieveGuestCartItem("bbb");
        Assertions.assertEquals(cartItemResponseList1.size(), 0);

        List<CartItemResponse> cartItemResponseList2 = cartItemRetrieveService.retrieveGuestCartItem("ccc");
        Assertions.assertEquals(cartItemResponseList2.size(), 0);

        Cart cart1 = cartRepository.findByAuthId("aaa").orElseThrow();
        cart1.updateExpiryDate(LocalDate.now().minusDays(7));
        cartRepository.save(cart1);
        List<CartItemResponse> cartItemResponseList3 = cartItemRetrieveService.retrieveGuestCartItem("aaa");
        Assertions.assertEquals(cartItemResponseList3.size(), 0);
    }
}
