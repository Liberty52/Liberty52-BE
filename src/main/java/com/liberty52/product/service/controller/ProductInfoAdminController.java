package com.liberty52.product.service.controller;

import com.liberty52.product.global.util.Validator;
import com.liberty52.product.service.applicationservice.*;
import com.liberty52.product.service.controller.dto.AdminProductDeliveryOptionsDto;
import com.liberty52.product.service.controller.dto.ProductInfoRetrieveResponseDto;
import com.liberty52.product.service.controller.dto.ProductIntroductionCreateDto;
import com.liberty52.product.service.controller.dto.ProductOptionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "상품 정보", description = "상품 정보 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class ProductInfoAdminController {
    private final ProductInfoRetrieveService productInfoRetrieveService;
    private final ProductIntroductionUpsertService productIntroductionUpsertService;
    private final ProductIntroductionDeleteService productIntroductionDeleteService;
    private final ProductIntroductionImgSaveService productIntroductionImgSaveService;
    private final ProductDeliveryOptionService productDeliveryOptionService;

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

    @Operation(summary = "상품 소개 생성 및 수정", description = "관리자가 특정 상품의 소개(html)를 생성 또는 수정합니다.")
    @PatchMapping("/admin/product/{productId}/introduction")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upsertProductIntroductionByAdmin(@RequestHeader("LB-Role") String role, @PathVariable String productId,
        @Validated @RequestBody ProductIntroductionCreateDto dto) {
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

    @Operation(summary = "상품의 배송옵션 추가", description = "관리자가 상품의 배송옵션을 추가합니다.")
    @PostMapping("/admin/products/{productId}/deliveryOptions")
    @ResponseStatus(HttpStatus.OK)
    public AdminProductDeliveryOptionsDto.Response createProductDeliveryOptions(
            @RequestHeader("LB-Role") String role,
            @PathVariable("productId") String productId,
            @Validated @RequestBody AdminProductDeliveryOptionsDto.Request dto
    ) {
        Validator.isAdmin(role);
        return productDeliveryOptionService.create(role, productId, dto);
    }

    @Operation(summary = "상품의 배송옵션 조회", description = "관리자가 상품의 배송옵션을 조회합니다.")
    @GetMapping("/admin/products/{productId}/deliveryOptions")
    @ResponseStatus(HttpStatus.OK)
    public AdminProductDeliveryOptionsDto.Response getProductDeliveryOptionByProduct(
            @RequestHeader("LB-Role") String role,
            @PathVariable("productId") String productId
    ) {
        Validator.isAdmin(role);
        return productDeliveryOptionService.getByProductIdForAdmin(role, productId);
    }

    @Operation(summary = "상품의 배송옵션 수정", description = "관리자가 상품의 배송옵션을 수정합니다.")
    @PutMapping("/admin/products/{productId}/deliveryOptions")
    @ResponseStatus(HttpStatus.OK)
    public AdminProductDeliveryOptionsDto.Response updateProductDeliveryOptions(
            @RequestHeader("LB-Role") String role,
            @PathVariable("productId") String productId,
            @Validated @RequestBody AdminProductDeliveryOptionsDto.Request dto
    ) {
        Validator.isAdmin(role);
        return productDeliveryOptionService.update(role, productId, dto);
    }
}
