package com.liberty52.product.service.applicationservice.impl;

import com.liberty52.product.global.adapter.s3.S3UploaderApi;
import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.product.global.util.Validator;
import com.liberty52.product.service.applicationservice.ProductIntroductionModifyService;
import com.liberty52.product.service.entity.Product;
import com.liberty52.product.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
@Transactional
public class ProductIntroductionModifyServiceImpl implements ProductIntroductionModifyService {
    private final ProductRepository productRepository;
    private final S3UploaderApi s3Uploader;

    @Override
    public void modifyProductIntroduction(String role, String productId, MultipartFile productIntroductionImageFile) {
        Validator.isAdmin(role);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ID", productId));
        if (productIntroductionImageFile != null) {
            //delete previous image
            String previousImageUrl = product.getProductIntroductionImageUrl();
            if(previousImageUrl!=null) s3Uploader.delete(previousImageUrl);
            //upload new image
            String imageUrl = s3Uploader.upload(productIntroductionImageFile);
            product.createProductIntroduction(imageUrl);
        }
        productRepository.save(product);
    }
}
