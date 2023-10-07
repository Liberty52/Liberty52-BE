package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.ProductInfoRetrieveService;
import com.liberty52.product.service.controller.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "상품", description = "상품 관련 API를 제공합니다")
public class ProductInfoRetrieveController {
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

    @Operation(summary = "관리자용 상품 옵션 정보 조회", description = "관리자가 특정 상품의 옵션 정보를 조회합니다.")
    @GetMapping("/admin/productOptionInfo/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductOptionResponseDto> retrieveProductOptionInfoListByAdmin(@RequestHeader("LB-Role") String role, @PathVariable String productId, @RequestParam boolean onSale) {
        return productInfoRetrieveService.retrieveProductOptionInfoListByAdmin(role, productId, onSale);
    }

    @Operation(summary = "관리자용 상품 목록 조회", description = "관리자가 상품 목록을 조회합니다.")
    @GetMapping("/admin/productInfo")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductInfoRetrieveResponseDto> retrieveProductListByAdmin(@RequestHeader("LB-Role") String role) {
        return productInfoRetrieveService.retrieveProductListByAdmin(role);
    }

    @Operation(summary = "관리자용 상품 조회", description = "관리자가 특정 상품을 조회합니다.")
    @GetMapping("/admin/productInfo/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductInfoRetrieveResponseDto retrieveProductByAdmin(@RequestHeader("LB-Role") String role, @PathVariable String productId) {
        return productInfoRetrieveService.retrieveProductByAdmin(role, productId);
    }

    @Operation(summary = "장바구니 상품 옵션 정보 조회", description = "사용자의 장바구니에 담긴 상품 옵션 정보를 조회합니다.")
    @GetMapping("/carts/productOptionInfo")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductInfoByCartResponseDto> retrieveProductOptionListByCart(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId) {
        return productInfoRetrieveService.retrieveProductOptionListByCart(authId);
    }
}
