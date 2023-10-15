package com.liberty52.product.service.controller.guest;

import com.liberty52.product.service.applicationservice.CartItemModifyService;
import com.liberty52.product.service.controller.dto.CartModifyRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "장바구니", description = "장바구니 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class GuestCartItemModifyController {

    private final CartItemModifyService cartItemModifyService;


    @Operation(summary = "비회원 장바구니 상품 수정", description = "비회원 장바구니에 있는 상품을 수정합니다.")
    @PatchMapping("/guest/carts/custom-products/{customProductId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyGuestCartItem(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String guestId,
            @RequestPart CartModifyRequestDto dto,
            @RequestPart(value = "file",required = false) MultipartFile imageFile,
            @PathVariable String customProductId
    ) {
        cartItemModifyService.modifyGuestCartItem(guestId,dto,imageFile,customProductId);
    }

}
