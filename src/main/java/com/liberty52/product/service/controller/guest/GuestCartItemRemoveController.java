package com.liberty52.product.service.controller.guest;

import com.liberty52.product.service.applicationservice.CartItemRemoveService;
import com.liberty52.product.service.controller.dto.CartItemListRemoveRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "장바구니", description = "장바구니 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class GuestCartItemRemoveController {

    private final CartItemRemoveService cartItemRemoveService;

    @Operation(summary = "비회원 장바구니 상품 제거", description = "비회원 장바구니에서 특정 상품을 제거합니다.")
    @DeleteMapping("/guest/carts/custom-products/{customProductId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void guestCartItemRemove(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String guestId,
            @PathVariable String customProductId
    ) {
        cartItemRemoveService.removeGuestCartItem(guestId, customProductId);
    }

    @Operation(summary = "비회원 장바구니 상품 목록 제거", description = "비회원 장바구니에서 여러 상품을 한 번에 제거합니다.")
    @DeleteMapping("/guest/carts/custom-products")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void guestCartItemListRemove(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String guestId,
            @RequestBody @Validated CartItemListRemoveRequestDto dto
    ) {
        cartItemRemoveService.removeGuestCartItemList(guestId, dto);
    }

}
