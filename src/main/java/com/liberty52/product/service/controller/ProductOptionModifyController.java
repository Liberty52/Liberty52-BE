package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.ProductOptionModifyService;
import com.liberty52.product.service.controller.dto.ProductOptionModifyRequestDto;
import com.liberty52.product.service.controller.dto.ProductOptionOnSailModifyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ProductOptionModifyController {

    private final ProductOptionModifyService productOptionModifyService;

    @PutMapping("/admin/productOption/{productOptionId}")
    @ResponseStatus(HttpStatus.OK)
    public void modifyProductOptionByAdmin(@RequestHeader("LB-Role") String role, @PathVariable String productOptionId, @Validated @RequestBody ProductOptionModifyRequestDto dto) {
        productOptionModifyService.modifyProductOptionByAdmin(role, productOptionId, dto);
    }

    @PatchMapping("/admin/productOption/{productOptionId}")
    @ResponseStatus(HttpStatus.OK)
    public void modifyProductOptionOnSailStateByAdmin(@RequestHeader("LB-Role") String role, @PathVariable String productOptionId, @Validated @RequestBody ProductOptionOnSailModifyRequestDto dto) {
        productOptionModifyService.modifyProductOptionOnSailStateByAdmin(role, productOptionId, dto);
    }
}
