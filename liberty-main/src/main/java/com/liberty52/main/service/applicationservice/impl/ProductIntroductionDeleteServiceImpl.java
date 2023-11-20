package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.global.util.Validator;
import com.liberty52.main.service.applicationservice.ProductIntroductionDeleteService;
import com.liberty52.main.service.entity.Product;
import com.liberty52.main.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductIntroductionDeleteServiceImpl implements ProductIntroductionDeleteService {
    private final ProductRepository productRepository;

    @Override
    public void deleteProductIntroduction(String role, String productId) {
        Validator.isAdmin(role);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product", "id", productId));
        product.deleteContent();
    }
}
