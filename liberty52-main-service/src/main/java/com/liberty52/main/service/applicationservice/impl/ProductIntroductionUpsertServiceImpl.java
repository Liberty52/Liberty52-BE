package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.global.util.Validator;
import com.liberty52.main.service.applicationservice.ProductIntroductionUpsertService;
import com.liberty52.main.service.entity.Product;
import com.liberty52.main.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductIntroductionUpsertServiceImpl implements ProductIntroductionUpsertService {
    private final ProductRepository productRepository;

    @Override
    public void upsertProductIntroductionByAdmin(String role, String productId, String content) {
        Validator.isAdmin(role);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product", "id", productId));
        product.createContent(content);
    }
}
