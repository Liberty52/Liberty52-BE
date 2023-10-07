package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.CartItemCreateService;
import com.liberty52.product.service.controller.dto.CartItemRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "장바구니", description = "장바구니 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class CartItemCreateController {
    private final CartItemCreateService cartItemCreateService;

    @Operation(summary = "비회원 장바구니 상품 생성", description = "주어진 인증 ID, 이미지 파일 및 장바구니 상품 요청을 사용하여 비회원 장바구니 상품을 생성합니다.")
    @PostMapping("/carts/custom-products")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAuthCartItem(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId, @RequestPart(value = "file") MultipartFile imageFile, @RequestPart CartItemRequest dto) {
        cartItemCreateService.createAuthCartItem(authId, imageFile, dto);
    }

}
