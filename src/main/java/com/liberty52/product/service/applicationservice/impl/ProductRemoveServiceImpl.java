package com.liberty52.product.service.applicationservice.impl;

import com.liberty52.product.global.adapter.s3.S3UploaderApi;
import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.product.global.util.Validator;
import com.liberty52.product.service.applicationservice.ProductRemoveService;
import com.liberty52.product.service.entity.Product;
import com.liberty52.product.service.repository.CustomProductRepository;
import com.liberty52.product.service.repository.ProductOptionRepository;
import com.liberty52.product.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductRemoveServiceImpl implements ProductRemoveService {
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final CustomProductRepository customProductRepository;
    private final S3UploaderApi s3Uploader;

    @Override
    public void removeProductByAdmin(String role, String productId) {
        Validator.isAdmin(role);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ID", productId));
        String productImageUrl = product.getPictureUrl();
        if(productImageUrl != null) s3Uploader.delete(productImageUrl);
        customProductRepository.deleteByProduct(product);
        productRepository.delete(product);
    }
}
