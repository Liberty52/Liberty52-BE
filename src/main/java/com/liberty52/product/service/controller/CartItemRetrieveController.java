package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.CartItemRetrieveService;
import com.liberty52.product.service.controller.dto.CartItemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "장바구니", description = "장바구니 관련 API를 제공합니다.")
public class CartItemRetrieveController {

    private final CartItemRetrieveService cartItemRetrieveService;

    @GetMapping("/carts")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "비회원 장바구니 상품 목록 조회", description = "주어진 인증 ID를 사용하여 비회원 장바구니 상품 목록을 조회합니다.")
    public List<CartItemResponse> retrieveAuthCartItem(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId) {
        return cartItemRetrieveService.retrieveAuthCartItem(authId);
    }

}
