package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.ProductInfoRetrieveService;
import com.liberty52.product.service.applicationservice.ProductIntroductionDeleteService;
import com.liberty52.product.service.applicationservice.ProductIntroductionImgSaveService;
import com.liberty52.product.service.applicationservice.ProductIntroductionUpsertService;
import com.liberty52.product.service.controller.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "상품 정보", description = "상품 정보 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class ProductInfoController {
    private final ProductInfoRetrieveService productInfoRetrieveService;
    private final ProductIntroductionUpsertService productIntroductionUpsertService;
    private final ProductIntroductionDeleteService productIntroductionDeleteService;
    private final ProductIntroductionImgSaveService productIntroductionImgSaveService;

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

    @Operation(summary = "상품 소개 생성 및 수정", description = "관리자가 특정 상품의 소개(html)를 생성 또는 수정합니다.")
    @PatchMapping("/admin/product/{productId}/introduction")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upsertProductIntroductionByAdmin(@RequestHeader("LB-Role") String role, @PathVariable String productId,
        @Validated ProductIntroductionCreateDto dto) {
        productIntroductionUpsertService.upsertProductIntroductionByAdmin(role, productId, dto.getContent());
    }

    @Operation(summary = "상품 소개 삭제", description = "관리자가 특정 상품의 소개를 삭제합니다.")
    @DeleteMapping("/admin/product/{productId}/introduction")
    @ResponseStatus(HttpStatus.OK)
    public void modifyProductIntroductionByAdmin(@RequestHeader("LB-Role") String role, @PathVariable String productId) {
        productIntroductionDeleteService.deleteProductIntroduction(role, productId);
    }

    @Operation(summary = "상품 소개 이미지 url 반환", description = "관리자가 상품 소개 이미지 url를 반환합니다.")
    @PostMapping("/admin/productIntroduction/img")
    @ResponseStatus(HttpStatus.OK)
    public String saveProductIntroductionImageByAdmin(@RequestHeader("LB-Role") String role,
		@RequestPart(value = "file") MultipartFile imageFile) {
		return productIntroductionImgSaveService.saveProductIntroductionImageByAdmin(role,imageFile);
    }
}
