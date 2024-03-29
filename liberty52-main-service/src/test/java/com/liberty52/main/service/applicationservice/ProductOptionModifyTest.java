package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.ProductOptionModifyRequestDto;
import com.liberty52.main.service.entity.Product;
import com.liberty52.main.service.entity.ProductOption;
import com.liberty52.main.service.repository.ProductOptionRepository;
import com.liberty52.main.service.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.liberty52.main.global.constants.RoleConstants.ADMIN;

@SpringBootTest
@Transactional
public class ProductOptionModifyTest {

    @Autowired
    ProductOptionModifyService productOptionModifyService;

    @Autowired
    ProductOptionRepository productOptionRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    void 옵션수정() {
        Product product = productRepository.findByName("Liberty 52_Frame").orElseGet(null);
        ProductOption productOption = product.getProductOptions().get(0);

        String optionId = productOption.getId();
        String name = "테스트";
        boolean require = false;
        boolean onSail = productOption.isOnSale();

        ProductOptionModifyRequestDto dto = ProductOptionModifyRequestDto.create(name, require, !onSail);
        productOptionModifyService.modifyProductOptionByAdmin(ADMIN, optionId, dto);

        ProductOption afterProductOption = productOptionRepository.findById(optionId).orElse(null);
        Assertions.assertEquals(afterProductOption.isOnSale(), !onSail);
        Assertions.assertEquals(afterProductOption.getName(), name);
        Assertions.assertEquals(afterProductOption.isRequire(), require);
    }

    @Test
    void 옵션삭제상태() {
        Product product = productRepository.findByName("Liberty 52_Frame").orElseGet(null);
        ProductOption productOption = product.getProductOptions().get(0);

        String optionId = productOption.getId();
        boolean onSail = productOption.isOnSale();

        productOptionModifyService.modifyProductOptionOnSailStateByAdmin(ADMIN, optionId);

        ProductOption afterProductOption = productOptionRepository.findById(optionId).orElse(null);
        Assertions.assertEquals(afterProductOption.isOnSale(), !onSail);

    }


}
