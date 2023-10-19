package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.ProductInfoRetrieveService;
import com.liberty52.product.service.applicationservice.ProductIntroductionCreateService;
import com.liberty52.product.service.applicationservice.ProductIntroductionModifyService;
import com.liberty52.product.service.controller.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "상품 정보", description = "상품 정보 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class ProductInfoController {
    private final ProductInfoRetrieveService productInfoRetrieveService;
    private final ProductIntroductionCreateService productIntroductionCreateService;
    private final ProductIntroductionModifyService productIntroductionModifyService;

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

    @Operation(summary = "상품 소개 생성", description = "관리자가 특정 상품의 소개 이미지를 업로드하여 상품 소개를 생성합니다.")
    @PostMapping("/admin/product/{productId}/introduction")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProductIntroductionByAdmin(@RequestHeader("LB-Role") String role, @PathVariable String productId,
                                                 @RequestPart(value = "images",required = false) MultipartFile productIntroductionImageFile) {
        productIntroductionCreateService.createProductIntroduction(role, productId, productIntroductionImageFile);
    }

    @Operation(summary = "상품 소개 수정", description = "관리자가 특정 상품의 소개 이미지를 수정합니다.")
    @PatchMapping("/admin/product/{productId}/introduction")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyProductIntroductionByAdmin(@RequestHeader("LB-Role") String role, @PathVariable String productId,
                                                 @RequestPart(value = "images",required = false) MultipartFile productIntroductionImageFile){
        productIntroductionModifyService.modifyProductIntroduction(role, productId, productIntroductionImageFile);
    }
}
