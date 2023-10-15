package com.liberty52.product.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.liberty52.product.service.applicationservice.ProductOptionModifyService;
import com.liberty52.product.service.controller.dto.ProductOptionModifyRequestDto;

@Tag(name = "상품", description = "상품 관련 API를 제공합니다")
@RequiredArgsConstructor
@RestController
public class ProductOptionModifyController {

    private final ProductOptionModifyService productOptionModifyService;

    @Operation(summary = "상품 옵션 수정", description = "관리자가 특정 상품 옵션을 수정합니다.")
    @PutMapping("/admin/productOption/{productOptionId}")
    @ResponseStatus(HttpStatus.OK)
    public void modifyProductOptionByAdmin(@RequestHeader("LB-Role") String role, @PathVariable String productOptionId, @Validated @RequestBody ProductOptionModifyRequestDto dto) {
        productOptionModifyService.modifyProductOptionByAdmin(role, productOptionId, dto);
    }

    @Operation(summary = "상품 옵션 판매 상태 수정", description = "관리자가 특정 상품 옵션의 판매 상태를 수정합니다.")
    @PutMapping("/admin/productOptionOnSale/{productOptionId}")
    @ResponseStatus(HttpStatus.OK)
    public void modifyProductOptionOnSailStateByAdmin(@RequestHeader("LB-Role") String role, @PathVariable String productOptionId) {
        productOptionModifyService.modifyProductOptionOnSailStateByAdmin(role, productOptionId);
    }

}
