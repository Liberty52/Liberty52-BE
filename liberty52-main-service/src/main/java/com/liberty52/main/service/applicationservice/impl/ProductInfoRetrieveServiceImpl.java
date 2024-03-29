package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.global.util.Validator;
import com.liberty52.main.service.applicationservice.ProductInfoRetrieveService;
import com.liberty52.main.service.controller.dto.*;
import com.liberty52.main.service.entity.*;
import com.liberty52.main.service.repository.CartRepository;
import com.liberty52.main.service.repository.ProductRepository;
import com.liberty52.main.service.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductInfoRetrieveServiceImpl implements ProductInfoRetrieveService {
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final CartRepository cartRepository;

    @Override
    public ProductListResponseDto retrieveProductList(Pageable pageable) {
        List<Review> reviewList = reviewRepository.findAll();
        return new ProductListResponseDto(productRepository.findByProductStateNot(ProductState.NOT_SALE, pageable), reviewList);
    }

    @Override
    public ProductDetailResponseDto retrieveProductDetail(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product", "id", productId));
        return new ProductDetailResponseDto(product);
    }

    @Override
    public List<ProductOptionResponseDto> retrieveProductOptionInfoListByAdmin(String role, String productId, boolean onSale) {
        Validator.isAdmin(role);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product", "id", productId));
        if (product.getProductOptions().size() == 0) {
            return Collections.emptyList();
        }

        if (onSale) {
            return product.getProductOptions().stream().filter(ProductOption::isOnSale).map(productOption -> new ProductOptionResponseDto(productOption, onSale)).collect(Collectors.toList());
        } else {
            return product.getProductOptions().stream().sorted(Comparator.comparing(ProductOption::isOnSale).reversed()).map(productOption -> new ProductOptionResponseDto(productOption, onSale)).collect(Collectors.toList());

        }

    }

    @Override
    public List<ProductInfoRetrieveResponseDto> retrieveProductListByAdmin(String role) {
        Validator.isAdmin(role);
        List<ProductInfoRetrieveResponseDto> dto = new ArrayList<>();
        List<Product> productList = productRepository.findAll();
        List<Review> reviewList = reviewRepository.findAll();

        if (productList.size() == 0) {
            throw new ResourceNotFoundException("product");
        }

        for (Product product : productList) {
            List<Review> productReviewList = reviewList.stream().filter(r -> r.getCustomProduct().getProduct().equals(product)).collect(Collectors.toList());
            float meanRate = product.getRate(productReviewList);
            dto.add(ProductInfoRetrieveResponseDto.of(product.getId(), product.getPictureUrl(), product.getName(), product.getPrice(), meanRate, productReviewList.size(), product.isCustom(), product.getContent(), product.getProductState(), product.getProductOrder()));
        }
        dto.sort(Comparator.comparingInt(ProductInfoRetrieveResponseDto::getOrder));
        return dto;
    }

    @Override
    public ProductInfoRetrieveResponseDto retrieveProductByAdmin(String role, String productId) {
        Validator.isAdmin(role);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product", "id", productId));
        List<Review> productReviewList = reviewRepository.findByCustomProduct_Product(product);
        float meanRate = product.getRate(productReviewList);
        return ProductInfoRetrieveResponseDto.of(product.getId(), product.getPictureUrl(), product.getName(), product.getPrice(), meanRate, productReviewList.size(), product.isCustom(), product.getContent(), product.getProductState(), product.getProductOrder());
    }

    @Override
    public List<ProductInfoByCartResponseDto> retrieveProductOptionListByCart(String authId) {
        Cart cart = cartRepository.findByAuthId(authId).orElse(null);
        List<ProductInfoByCartResponseDto> productInfoByCartResponseDtoList = new ArrayList<>();
        if (cart == null || cart.getCustomProducts().size() == 0) {
            return productInfoByCartResponseDtoList;
        }

        List<Product> productList = cart.getCustomProducts().stream().map(c -> c.getProduct()).distinct().collect(Collectors.toList());
        for (Product product : productList) {
            productInfoByCartResponseDtoList.add(new ProductInfoByCartResponseDto(product));
        }

        return productInfoByCartResponseDtoList;
    }

}
