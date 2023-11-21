package com.liberty52.main.service.controller;

import com.liberty52.main.service.applicationservice.ProductOptionCreateService;
import com.liberty52.main.service.applicationservice.ProductOptionModifyService;
import com.liberty52.main.service.controller.dto.CreateProductOptionRequestDto;
import com.liberty52.main.service.controller.dto.ProductOptionModifyRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "상품", description = "상품 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class ProductAdminOptionController {
    private final ProductOptionCreateService productOptionCreateService;
    private final ProductOptionModifyService productOptionModifyService;

    @Operation(summary = "상품 옵션 생성", description = "관리자가 특정 상품에 옵션을 생성합니다.")
    @PostMapping("/admin/productOption/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProductOptionByAdmin(@RequestHeader("LB-Role") String role,
                                           @Validated @RequestBody CreateProductOptionRequestDto dto, @PathVariable String productId) {
        productOptionCreateService.createProductOptionByAdmin(role, dto, productId);
    }

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
