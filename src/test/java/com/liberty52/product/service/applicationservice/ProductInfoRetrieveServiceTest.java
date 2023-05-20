package com.liberty52.product.service.applicationservice;

import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.product.service.controller.dto.ProductOptionDetailResponseDto;
import com.liberty52.product.service.controller.dto.ProductOptionResponseDto;
import com.liberty52.product.service.entity.*;
import com.liberty52.product.service.repository.ProductRepository;
import com.liberty52.product.service.repository.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.liberty52.product.global.contants.RoleConstants.ADMIN;

@SpringBootTest
@Transactional
public class ProductInfoRetrieveServiceTest {

    @Autowired
    ProductInfoRetrieveService productInfoRetrieveService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @BeforeEach
    void beforeEach() {

    }

    @Test
    void 상품옵션조회(){
        Product product = productRepository.findById("LIB-001").orElseGet(null);

        List<ProductOptionResponseDto> productOptionResponseDtoList=productInfoRetrieveService.retrieveProductOptionInfoList("LIB-001");
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productInfoRetrieveService.retrieveProductOptionInfoList("null"));

        Assertions.assertEquals(productOptionResponseDtoList.size(), product.getProductOptions().size());
        ProductOptionResponseDto optionDto = productOptionResponseDtoList.get(0);
        ProductOption productOption = product.getProductOptions().get(0);

        Assertions.assertEquals(optionDto.getOptionName(), productOption.getName());
        Assertions.assertEquals(optionDto.isOnSail(), productOption.isOnSale());
        Assertions.assertEquals(optionDto.isRequire(), productOption.isRequire());

        Assertions.assertEquals(optionDto.getOptionDetailList().size(), productOption.getOptionDetails().size());
        ProductOptionDetailResponseDto detailDto = optionDto.getOptionDetailList().get(0);
        OptionDetail optionDetail = productOption.getOptionDetails().get(0);

        Assertions.assertEquals(detailDto.getOptionDetailName(), optionDetail.getName());
        Assertions.assertEquals(detailDto.isOnSail(), optionDetail.isOnSale());
        Assertions.assertEquals(detailDto.getPrice(), optionDetail.getPrice());

    }

}