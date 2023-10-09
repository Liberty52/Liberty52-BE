package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.CartItemModifyService;
import com.liberty52.product.service.controller.dto.CartModifyRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "장바구니", description = "장바구니 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class CartItemModifyController {

  private final CartItemModifyService cartItemModifyService;

  @Operation(summary = "장바구니 상품 수정", description = "주어진 인증 ID, 이미지 파일 및 장바구니 상품 수정 요청을 사용하여 사용자 장바구니 상품을 수정합니다.")
  @PatchMapping("/carts/custom-products/{customProductId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void modifyUserCartItem(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId, @RequestPart CartModifyRequestDto dto,
                                 @RequestPart(value = "file", required = false) MultipartFile imageFile, @PathVariable String customProductId) {
    cartItemModifyService.modifyUserCartItem(authId, dto, imageFile, customProductId);
  }

}