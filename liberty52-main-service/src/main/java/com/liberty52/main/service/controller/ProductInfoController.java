package com.liberty52.main.service.controller;

import com.liberty52.main.service.applicationservice.ProductInfoRetrieveService;
import com.liberty52.main.service.controller.dto.ProductDetailResponseDto;
import com.liberty52.main.service.controller.dto.ProductInfoByCartResponseDto;
import com.liberty52.main.service.controller.dto.ProductListResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "상품 정보", description = "상품 정보 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class ProductInfoController {
    private final ProductInfoRetrieveService productInfoRetrieveService;

    @Operation(summary = "상품 목록 조회", description = "상품 목록을 조회합니다.")
    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public ProductListResponseDto retrieveProductList(Pageable pageable) {
        return productInfoRetrieveService.retrieveProductList(pageable);
    }

    @Operation(summary = "상품 상세 조회", description = "상품의 상세 정보를 조회합니다.")
    @GetMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDetailResponseDto retrieveProductDetail(@PathVariable String productId) {
        return productInfoRetrieveService.retrieveProductDetail(productId);
    }

    /*TODO: 관련 도메인으로 분리*/
    @Operation(summary = "장바구니 상품 옵션 정보 조회", description = "사용자의 장바구니에 담긴 상품 옵션 정보를 조회합니다.")
    @GetMapping("/carts/productOptionInfo")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductInfoByCartResponseDto> retrieveProductOptionListByCart(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId) {
        return productInfoRetrieveService.retrieveProductOptionListByCart(authId);
    }

}
