package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.CartItemRemoveService;
import com.liberty52.product.service.controller.dto.CartItemListRemoveRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "장바구니", description = "장바구니 관련 API를 제공합니다.")
public class CartItemRemoveController {
    private final CartItemRemoveService cartItemRemoveService;

    @DeleteMapping("/carts/custom-products/{customProductId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "비회원 장바구니 상품 삭제", description = "주어진 인증 ID 및 장바구니 상품 ID를 사용하여 비회원 장바구니 상품을 삭제합니다.")
    public void removeCartItem(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId, @PathVariable String customProductId) {
        cartItemRemoveService.removeCartItem(authId, customProductId);
    }

    @DeleteMapping("/carts/custom-products")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "비회원 장바구니 상품 목록 삭제", description = "주어진 인증 ID 및 장바구니 상품 ID 목록을 사용하여 비회원 장바구니 상품 목록을 삭제합니다.")
    public void removeCartItemList(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
                                   @RequestBody @Validated CartItemListRemoveRequestDto dto) {
        cartItemRemoveService.removeCartItemList(authId, dto);
    }

}
