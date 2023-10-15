package com.liberty52.product.service.controller.guest;

import com.liberty52.product.service.applicationservice.CartItemCreateService;
import com.liberty52.product.service.controller.dto.CartItemRequest;
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
public class GuestCartItemCreateController {

    private final CartItemCreateService cartItemCreateService;

    @Operation(summary = "비회원 장바구니에 상품 추가", description = "비회원 장바구니에 상품을 추가합니다.")
    @PostMapping("/guest/carts/custom-products")
    @ResponseStatus(HttpStatus.CREATED)
    public void createGuestCartItem(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String guestId,
            @RequestPart(value = "file") MultipartFile imageFile,
            @RequestPart CartItemRequest dto
    ) {
        cartItemCreateService.createGuestCartItem(guestId, imageFile, dto);
    }

}
