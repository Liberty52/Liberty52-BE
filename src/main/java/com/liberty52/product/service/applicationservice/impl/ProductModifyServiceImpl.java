package com.liberty52.product.service.applicationservice.impl;

import com.liberty52.product.global.adapter.s3.S3UploaderApi;
import com.liberty52.product.global.exception.external.badrequest.NoRequestedDataException;
import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.product.global.util.Validator;
import com.liberty52.product.service.applicationservice.ProductModifyService;
import com.liberty52.product.service.controller.dto.ProductCreateRequestDto;
import com.liberty52.product.service.controller.dto.ProductModifyRequestDto;
import com.liberty52.product.service.entity.Product;
import com.liberty52.product.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductModifyServiceImpl implements ProductModifyService {
    private final ProductRepository productRepository;
    private final S3UploaderApi s3Uploader;
    @Qualifier("modelMapperPatch")
    private final ModelMapper modelMapperPatch;

    @Override
    public Product modifyProductByAdmin(String role, String productId, ProductModifyRequestDto productRequestDto, MultipartFile productImage) {
        if(productRequestDto==null&&productImage==null) throw new NoRequestedDataException();
        Validator.isAdmin(role);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ID", productId));

        if (productImage != null){
            //delete previous image
            String previousImageUrl = product.getPictureUrl();
            if(previousImageUrl!=null) s3Uploader.delete(previousImageUrl);
            //upload new image
            String newImageUrl = s3Uploader.upload(productImage);
            product.setProductPictureUrl(newImageUrl);
        }

        if(productRequestDto != null) {
            //copy fields from DTO to Entity
            modelMapperPatch.map(productRequestDto, product);
        }
        return productRepository.save(product);
    }
}
