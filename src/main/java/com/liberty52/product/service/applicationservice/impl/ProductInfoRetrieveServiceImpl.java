package com.liberty52.product.service.applicationservice.impl;

import com.liberty52.product.global.exception.external.forbidden.InvalidRoleException;
import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.product.service.applicationservice.ProductInfoRetrieveService;
import com.liberty52.product.service.controller.dto.ProductDetailResponseDto;
import com.liberty52.product.service.controller.dto.ProductInfoRetrieveResponseDto;
import com.liberty52.product.service.controller.dto.ProductListResponseDto;
import com.liberty52.product.service.entity.Product;
import com.liberty52.product.service.entity.Review;
import com.liberty52.product.service.repository.ProductRepository;
import com.liberty52.product.service.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.liberty52.product.global.contants.RoleConstants.ADMIN;

@Service
@RequiredArgsConstructor
public class ProductInfoRetrieveServiceImpl implements ProductInfoRetrieveService {
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

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
    public List<ProductInfoRetrieveResponseDto> retrieveProductInfoList(String role) {
        if(!ADMIN.equals(role)){
            throw new InvalidRoleException(role);
        }
        List<ProductInfoRetrieveResponseDto> dto = new ArrayList<>();
        List<Product> productList = productRepository.findAll();
        List<Review> reviewList = reviewRepository.findAll();

        if(productList.size() == 0){
            throw new ResourceNotFoundException("product", "product", "null");
        }

        for (Product product : productList){
            int[] rate = getRate(reviewList.stream().filter(r -> r.getCustomProduct().getProduct().equals(product)).collect(Collectors.toList()));
            dto.add(ProductInfoRetrieveResponseDto.of(product.getId(), product.getPictureUrl(), product.getName(), product.getPrice(), rate[0]/rate[1], rate[1],product.getState()));
        }

        return dto;
    }

    private int[] getRate(List<Review> productReviewList) {
        if(productReviewList.size() > 0){
            return new int[]{productReviewList.stream().mapToInt(Review::getRating).sum(), productReviewList.size()};
        } else  {
            return new int[]{0, 0};
        }
    }

    @Override
    public ProductInfoRetrieveResponseDto retrieveProductInfo(String role, String productId) {
        if(!ADMIN.equals(role)){
            throw new InvalidRoleException(role);
        }
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product", "id", productId));
        int[] rate = getRate(reviewRepository.findByCustomProduct_Product(product));
        return ProductInfoRetrieveResponseDto.of(product.getId(), product.getPictureUrl(), product.getName(), product.getPrice(), rate[0]/rate[1], rate[1],product.getState());
    }
}
