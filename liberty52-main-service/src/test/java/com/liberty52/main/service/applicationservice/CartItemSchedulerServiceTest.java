package com.liberty52.main.service.applicationservice;

import com.liberty52.main.MockS3Test;
import com.liberty52.main.service.controller.dto.CartItemRequest;
import com.liberty52.main.service.entity.Cart;
import com.liberty52.main.service.entity.CustomProduct;
import com.liberty52.main.service.entity.CustomProductOption;
import com.liberty52.main.service.repository.CartRepository;
import com.liberty52.main.service.repository.CustomProductOptionRepository;
import com.liberty52.main.service.repository.CustomProductRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class CartItemSchedulerServiceTest extends MockS3Test {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CustomProductRepository customProductRepository;

    @Autowired
    CustomProductOptionRepository customProductOptionRepository;

    @Autowired
    CartItemCreateService cartItemCreateService;

    @Autowired
    CartItemSchedulerService cartItemSchedulerService;

    @Autowired
    EntityManager entityManager;

    List<String> productID = new ArrayList<>();
    List<String> optionId = new ArrayList<>();
    String authId = "guest";

    @BeforeEach
    void init() throws IOException {
        CartItemRequest dto1 = new CartItemRequest();
        String[] option1 = {"OPT-001", "OPT-003", "OPT-005"};
        dto1.create("LIB-001", 1, option1);

        CartItemRequest dto2 = new CartItemRequest();
        String[] option2 = {"OPT-002", "OPT-003", "OPT-006"};
        dto2.create("LIB-001", 2, option2);

        MockMultipartFile imageFile = new MockMultipartFile("image", "test.png", "image/jpeg", new FileInputStream("src/test/resources/static/test.jpg"));

        cartItemCreateService.createGuestCartItem(authId, imageFile, dto1);
        cartItemCreateService.createGuestCartItem(authId, imageFile, dto2);

        Cart cart = cartRepository.findByAuthId(authId).orElse(null);

        for (CustomProduct customProduct : cart.getCustomProducts()) {
            customProduct.getOptions().forEach(option -> optionId.add(option.getId()));
            productID.add(customProduct.getId());
        }

        cart.updateExpiryDate(LocalDate.now().minusDays(7));
        cartRepository.save(cart);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void 스케줄러삭제() throws IOException {
        Cart beforeCart = cartRepository.findByAuthId(authId).orElse(null);
        Assertions.assertEquals(beforeCart.getAuthId(), authId);
        cartItemSchedulerService.deleteNonMemberCart();
        Cart afterCart = cartRepository.findByAuthId(authId).orElse(null);
        //Cart afterCart = cartRepository.findById(beforeCart.getId()).orElse(null);
        Assertions.assertNull(afterCart);
        for (String product : productID) {
            CustomProduct customProduct = customProductRepository.findById(product).orElse(null);
            Assertions.assertNull(customProduct);
        }
        for (String option : optionId) {
            CustomProductOption customProductOption = customProductOptionRepository.findById(option).orElse(null);
            Assertions.assertNull(customProductOption);
        }

        //Cart afterCart = cartRepository.findByAuthId(authId).orElse(null);
        //Assertions.assertNull(afterCart);


    }

}
