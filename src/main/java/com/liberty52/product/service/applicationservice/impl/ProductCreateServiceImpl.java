package com.liberty52.product.service.applicationservice.impl;

import com.liberty52.product.global.adapter.s3.S3UploaderApi;
import com.liberty52.product.global.util.Validator;
import com.liberty52.product.service.applicationservice.ProductCreateService;
import com.liberty52.product.service.controller.dto.ProductRequestDto;
import com.liberty52.product.service.entity.Product;
import com.liberty52.product.service.repository.ProductRepository;
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
    public void createProductByAdmin(String role, ProductRequestDto productRequestDto, MultipartFile productImage) {
        Validator.isAdmin(role);
        String imageUrl = null;
        if(productImage!=null){
            imageUrl = s3Uploader.upload(productImage);
        }
        Product newProduct = Product.builder()
                .name(productRequestDto.getName())
                .productState(productRequestDto.getProductState())
                .price(productRequestDto.getPrice())
                .isCustom(productRequestDto.getIsCustom())
                .pictureUrl(imageUrl)
                .build();

        productRepository.save(newProduct);
    }

}
