package com.liberty52.main.service.applicationservice;

import com.liberty52.main.MockS3Test;
import com.liberty52.main.global.exception.external.notfound.OptionDetailNotFoundByNameException;
import com.liberty52.main.global.exception.external.notfound.ProductNotFoundByNameException;
import com.liberty52.main.service.controller.dto.CartItemRequest;
import com.liberty52.main.service.entity.Cart;
import com.liberty52.main.service.entity.CustomProduct;
import com.liberty52.main.service.entity.CustomProductOption;
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
public class CartItemCreateServiceTest extends MockS3Test {

    @Autowired
    CartItemCreateService cartItemCreateService;

    @Autowired
    CartRepository cartRepository;

    @Test
    void 장바구니생성() throws IOException {

        CartItemRequest dto1 = new CartItemRequest();
        String[] option = {"OPT-001", "OPT-003", "OPT-005"};
        dto1.create("LIB-001", 1, option);

        CartItemRequest dto2 = new CartItemRequest();
        dto2.create("L", 2, option);

        String[] optionErr = {"OPT-002", "OPT-003", "err"};
        CartItemRequest dto3 = new CartItemRequest();
        dto3.create("LIB-001", 4, optionErr);

        MockMultipartFile imageFile = new MockMultipartFile("image", "test.png", "image/jpeg", new FileInputStream("src/test/resources/static/test.jpg"));

        cartItemCreateService.createAuthCartItem("aaa", imageFile, dto1);

        Cart cart = cartRepository.findByAuthId("aaa").orElseThrow(() -> new RuntimeException());
        Assertions.assertEquals(cart.getAuthId(), "aaa");

        CustomProduct customProduct = cart.getCustomProducts().get(0);
        Assertions.assertEquals(customProduct.getQuantity(), 1);
        Assertions.assertEquals(customProduct.getProduct().getName(), "Liberty 52_Frame");


        List<CustomProductOption> productCartOptionList = customProduct.getOptions();
        System.out.println(customProduct.getOptions().size());

        Assertions.assertEquals(productCartOptionList.get(0).getOptionDetail().getName(), "이젤 거치형");
        Assertions.assertEquals(productCartOptionList.get(1).getOptionDetail().getName(), "1mm 두께 승화전사 인쇄용 알루미늄시트");
        Assertions.assertEquals(productCartOptionList.get(2).getOptionDetail().getName(), "무광실버");

        Assertions.assertThrows(ProductNotFoundByNameException.class, () -> cartItemCreateService.createAuthCartItem("aaa", imageFile, dto2));


        Assertions.assertThrows(OptionDetailNotFoundByNameException.class, () -> cartItemCreateService.createAuthCartItem("aaa", imageFile, dto3));


    }

    @Test
    void 게스트장바구니생성() throws IOException {

        CartItemRequest dto1 = new CartItemRequest();
        String[] option1 = {"OPT-001", "OPT-003", "OPT-005"};
        dto1.create("LIB-001", 1, option1);

        CartItemRequest dto2 = new CartItemRequest();
        String[] option2 = {"OPT-002", "OPT-003", "OPT-006"};
        dto2.create("LIB-001", 2, option2);

        MockMultipartFile imageFile = new MockMultipartFile("image", "test.png", "image/jpeg", new FileInputStream("src/test/resources/static/test.jpg"));

        cartItemCreateService.createGuestCartItem("guest", imageFile, dto1);
        cartItemCreateService.createGuestCartItem("guest", imageFile, dto2);

        Cart cart = cartRepository.findByAuthId("guest").orElseThrow(() -> new RuntimeException());
        Assertions.assertEquals(cart.getAuthId(), "guest");
        Assertions.assertEquals(cart.getExpiryDate(), LocalDate.now().plusDays(7));
        //Assertions.assertEquals(cart.getExpiryDate(), LocalDate.of(2023,04,27));

        CustomProduct customProduct = cart.getCustomProducts().get(0);
        Assertions.assertEquals(customProduct.getQuantity(), 1);
        Assertions.assertEquals(customProduct.getProduct().getName(), "Liberty 52_Frame");


        List<CustomProductOption> productCartOptionList = customProduct.getOptions();

        Assertions.assertEquals(customProduct.getOptions().size(), 3);
        Assertions.assertEquals(productCartOptionList.get(0).getOptionDetail().getName(), "이젤 거치형");
        Assertions.assertEquals(productCartOptionList.get(1).getOptionDetail().getName(), "1mm 두께 승화전사 인쇄용 알루미늄시트");
        Assertions.assertEquals(productCartOptionList.get(2).getOptionDetail().getName(), "무광실버");

        CustomProduct customProduct1 = cart.getCustomProducts().get(1);
        Assertions.assertEquals(customProduct1.getQuantity(), 2);
        Assertions.assertEquals(customProduct1.getProduct().getName(), "Liberty 52_Frame");


        List<CustomProductOption> productCartOptionList1 = customProduct1.getOptions();

        Assertions.assertEquals(customProduct1.getOptions().size(), 3);
        Assertions.assertEquals(productCartOptionList1.get(0).getOptionDetail().getName(), "벽걸이형");
        Assertions.assertEquals(productCartOptionList1.get(1).getOptionDetail().getName(), "1mm 두께 승화전사 인쇄용 알루미늄시트");
        Assertions.assertEquals(productCartOptionList1.get(2).getOptionDetail().getName(), "유광백색");


    }
}
