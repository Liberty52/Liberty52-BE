package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.ProductOptionCreateService;
import com.liberty52.product.service.controller.dto.CreateProductOptionRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "상품", description = "상품 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class ProductOptionCreateController {

    private final ProductOptionCreateService productOptionCreateService;

    @Operation(summary = "상품 옵션 생성", description = "관리자가 특정 상품에 옵션을 생성합니다.")
    @PostMapping("/admin/productOption/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProductOptionByAdmin(@RequestHeader("LB-Role") String role,
                                          @Validated @RequestBody CreateProductOptionRequestDto dto, @PathVariable String productId) {
        productOptionCreateService.createProductOptionByAdmin(role, dto, productId);
    }
}
