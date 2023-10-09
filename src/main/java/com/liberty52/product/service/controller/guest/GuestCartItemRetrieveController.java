package com.liberty52.product.service.controller.guest;

import com.liberty52.product.service.applicationservice.CartItemRetrieveService;
import com.liberty52.product.service.controller.dto.CartItemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "장바구니", description = "장바구니 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class GuestCartItemRetrieveController {

    private final CartItemRetrieveService cartItemRetrieveService;

    @Operation(summary = "비회원 장바구니 조회", description = "비회원의 장바구니에 있는 상품 목록을 조회합니다.")
    @GetMapping("/guest/carts")
    @ResponseStatus(HttpStatus.OK)
    public List<CartItemResponse> retrieveGuestCartItem(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String guestId
    ) {
        return cartItemRetrieveService.retrieveGuestCartItem(guestId);
    }

}
