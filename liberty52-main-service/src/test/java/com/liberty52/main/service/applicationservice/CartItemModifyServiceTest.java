package com.liberty52.main.service.applicationservice;

import com.liberty52.main.MockS3Test;
import com.liberty52.main.service.controller.dto.CartModifyRequestDto;
import com.liberty52.main.service.entity.Cart;
import com.liberty52.main.service.entity.CustomProduct;
import com.liberty52.main.service.entity.CustomProductOption;
import com.liberty52.main.service.entity.Product;
import com.liberty52.main.service.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CartItemModifyServiceTest extends MockS3Test {

  @Autowired
  CustomProductRepository customProductRepository;
  @Autowired
  ProductRepository productRepository;
  @Autowired
  ProductOptionRepository productOptionRepository;
  @Autowired
  CustomProductOptionRepository customProductOptionRepository;

  @Autowired
  OptionDetailRepository optionDetailRepository;
  @Autowired
  CartRepository cartRepository;
  @Autowired
  ApplicationEventPublisher eventPublisher;

  @Autowired
  CartItemModifyService cartItemModifyService;
  String productName = "Liberty 52_Frame";
  String detailId = "OPT-001";
  String authId = UUID.randomUUID().toString();
  String customProductId;
  MockMultipartFile imageFile = new MockMultipartFile("image", "test.png", "image/jpeg",
          new FileInputStream("src/test/resources/static/test.jpg"));
  CartItemModifyServiceTest() throws IOException {
  }

  @BeforeEach
  void beforeEach() {
    Cart cart = cartRepository.save(Cart.create(authId));
    CustomProduct customProduct = CustomProduct.create("example-url", 3, authId);
    customProduct.associateWithCart(cart);

    Product product = productRepository.findByName(productName).get();
    customProduct.associateWithProduct(product);

    customProductRepository.save(customProduct);

    CustomProductOption customProductOption = CustomProductOption.create();
    customProductOption.associate(optionDetailRepository.findById(detailId).get());
    customProductOption.associate(optionDetailRepository.findById("OPT-007").get());
    customProductOption.associate(customProduct);
    customProductOptionRepository.save(customProductOption);

    customProductId = customProduct.getId();
  }


  @Test
  void modify() {
    List<String> options = new ArrayList<>(List.of("OPT-006", "OPT-002")); //불변 객체를 ArrayList로 감싸 변할 수 있게
    int quantity = 5;
    CartModifyRequestDto cartModifyRequestDto = CartModifyRequestDto.create(options, quantity);

    cartItemModifyService.modifyUserCartItem(authId, cartModifyRequestDto, imageFile, customProductId);

    CustomProduct customProduct = customProductRepository.findById(customProductId).get();
    Assertions.assertEquals(quantity, customProduct.getQuantity());

    Assertions.assertEquals(options.size(), customProduct.getOptions().size());

    Collections.sort(options);
    List<String> actualList = customProduct.getOptions().stream()
            .map(cpo -> cpo.getOptionDetail().getId())
            .sorted()
            .toList();
    for (int i = 0; i < options.size(); i++) {
      Assertions.assertEquals(options.get(i), actualList.get(i));
    }
  }

}
