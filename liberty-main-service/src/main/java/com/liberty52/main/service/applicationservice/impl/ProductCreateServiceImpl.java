package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.main.global.adapter.s3.S3UploaderApi;
import com.liberty52.main.global.util.Validator;
import com.liberty52.main.service.applicationservice.ProductCreateService;
import com.liberty52.main.service.controller.dto.ProductCreateRequestDto;
import com.liberty52.main.service.entity.Product;
import com.liberty52.main.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductCreateServiceImpl implements ProductCreateService {
    private final ProductRepository productRepository;
    private final S3UploaderApi s3Uploader;

    @Override
    public Product createProductByAdmin(String role, ProductCreateRequestDto productRequestDto, MultipartFile productImage) {
        Validator.isAdmin(role);
        String imageUrl = null;
        if (productImage != null) {
            imageUrl = s3Uploader.upload(productImage);
        }
        Product newProduct = Product.builder()
                .name(productRequestDto.getName())
                .productState(productRequestDto.getProductState())
                .price(productRequestDto.getPrice())
                .isCustom(productRequestDto.getIsCustom())
                .pictureUrl(imageUrl)
                .order(productRequestDto.getOrder())
                .build();

        return productRepository.save(newProduct);
    }

}
