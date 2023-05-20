package com.liberty52.product.service.applicationservice.impl;

import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.product.service.applicationservice.ProductInfoRetrieveService;
import com.liberty52.product.service.controller.dto.ProductDetailResponseDto;
import com.liberty52.product.service.controller.dto.ProductListResponseDto;
import com.liberty52.product.service.controller.dto.ProductOptionDetailResponseDto;
import com.liberty52.product.service.controller.dto.ProductOptionResponseDto;
import com.liberty52.product.service.entity.OptionDetail;
import com.liberty52.product.service.entity.Product;
import com.liberty52.product.service.entity.ProductOption;
import com.liberty52.product.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductInfoRetrieveServiceImpl implements ProductInfoRetrieveService {
    private final ProductRepository productRepository;

    @Override
    public ProductListResponseDto retrieveProductList(Pageable pageable) {
        return new ProductListResponseDto(productRepository.findAll(pageable));
    }

    @Override
    public ProductDetailResponseDto retrieveProductDetail(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product", "id", productId));
        return new ProductDetailResponseDto(product);
    }

    @Override
    public List<ProductOptionResponseDto> retrieveProductOptionInfoList(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product", "id", productId));
        List<ProductOptionResponseDto> productOptionResponseDtoList = new ArrayList<>();
        for(ProductOption productOption : product.getProductOptions()){
            productOptionResponseDtoList.add(ProductOptionResponseDto.of(productOption.getName(), productOption.isRequire(), productOption.isOnSale(), getOptionDetails(productOption.getOptionDetails())));
        }
        return productOptionResponseDtoList;
    }

    private List<ProductOptionDetailResponseDto> getOptionDetails(List<OptionDetail> optionDetailList) {
        List<ProductOptionDetailResponseDto> productOptionDetailResponseDtoList = new ArrayList<>();
        for(OptionDetail optionDetail : optionDetailList){
            productOptionDetailResponseDtoList.add(ProductOptionDetailResponseDto.of(optionDetail.getName(), optionDetail.getPrice(), optionDetail.isOnSale()));
        }
        return productOptionDetailResponseDtoList;
    }
}
