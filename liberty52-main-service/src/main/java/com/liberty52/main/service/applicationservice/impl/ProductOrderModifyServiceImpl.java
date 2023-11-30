package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.service.applicationservice.ProductOrderModifyService;
import com.liberty52.main.service.entity.Product;
import com.liberty52.main.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductOrderModifyServiceImpl implements ProductOrderModifyService {

    private final ProductRepository productRepository;

    @Override
    public void modifyProductOrder(String[] productIdList) {
        for (int i = 0; i < productIdList.length; i++) {
            String productId = productIdList[i];
            Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ID", productId));
            product.updateProductOrder(i + 1);
        }
    }

}
