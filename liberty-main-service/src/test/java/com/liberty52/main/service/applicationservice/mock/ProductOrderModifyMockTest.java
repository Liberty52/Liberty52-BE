package com.liberty52.main.service.applicationservice.mock;

import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.liberty52.main.service.applicationservice.impl.ProductOrderModifyServiceImpl;
import com.liberty52.main.service.entity.Product;
import com.liberty52.main.service.entity.ProductState;
import com.liberty52.main.service.repository.ProductRepository;
import com.liberty52.main.service.utils.MockFactory;

@ExtendWith(MockitoExtension.class)
class ProductOrderModifyMockTest {
    @InjectMocks
    ProductOrderModifyServiceImpl productOrderModifyService;

    @Mock
    ProductRepository productRepository;

    @Test
    void 상품순서변경() {
        //given
        Product product1 = MockFactory.createProduct("Liberty52", ProductState.ON_SALE, 100L, true, 1);
        Product product2 = MockFactory.createProduct("Liberty53", ProductState.ON_SALE, 100L, true, 2);
        Product product3 = MockFactory.createProduct("Liberty54", ProductState.ON_SALE, 100L, true, 3);

        given(productRepository.findById(product1.getId())).willReturn(Optional.of(product1));
        given(productRepository.findById(product2.getId())).willReturn(Optional.of(product2));
        given(productRepository.findById(product3.getId())).willReturn(Optional.of(product3));

        String[] productIdList = {product2.getId(), product3.getId(), product1.getId()};
        //when
        productOrderModifyService.modifyProductOrder(productIdList);
        //then
        Assertions.assertEquals(1, product2.getProductOrder());
        Assertions.assertEquals(2, product3.getProductOrder());
        Assertions.assertEquals(3, product1.getProductOrder());
    }

}
