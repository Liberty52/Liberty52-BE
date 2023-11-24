package com.liberty52.main.service.controller;

import com.liberty52.main.service.applicationservice.CartItemCreateService;
import com.liberty52.main.service.applicationservice.CartItemModifyService;
import com.liberty52.main.service.applicationservice.CartItemRemoveService;
import com.liberty52.main.service.applicationservice.CartItemRetrieveService;
import com.liberty52.main.service.controller.dto.CartItemListRemoveRequestDto;
import com.liberty52.main.service.controller.dto.CartItemRequest;
import com.liberty52.main.service.controller.dto.CartItemResponse;
import com.liberty52.main.service.controller.dto.CartModifyRequestDto;
import com.liberty52.product.service.controller.dto.CartItemRequestWithLicense;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "장바구니", description = "장바구니 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemCreateService cartItemCreateService;
    private final CartItemRetrieveService cartItemRetrieveService;
    private final CartItemModifyService cartItemModifyService;
    private final CartItemRemoveService cartItemRemoveService;

    /**
     * CREATE
     **/
    @Operation(summary = "장바구니 상품 생성", description = "주어진 인증 ID, 이미지 파일 및 장바구니 상품 요청을 사용하여 사용자 장바구니 상품을 생성합니다.")
    @PostMapping("/carts/custom-products")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAuthCartItem(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId, @RequestPart(value = "file") MultipartFile imageFile, @RequestPart CartItemRequest dto) {
        cartItemCreateService.createAuthCartItem(authId, imageFile, dto);
    }

    /**
     * CREATE
     **/
    @Operation(summary = "장바구니 라이선스 상품 생성", description = "주어진 인증 ID, 이미지 파일 및 장바구니 상품 요청을 사용하여 사용자 장바구니 상품을 생성합니다.")
    @PostMapping("/carts/license-products")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAuthCartItemWithLicense(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId, @RequestPart(value = "file", required = false) MultipartFile imageFile, @RequestPart CartItemRequestWithLicense dto) {
        cartItemCreateService.createAuthCartItemWithLicense(authId, dto);
    }

    /**
     * READ
     **/
    @Operation(summary = "장바구니 상품 목록 조회", description = "주어진 인증 ID를 사용하여 사용자 장바구니 상품 목록을 조회합니다.")
    @GetMapping("/carts")
    @ResponseStatus(HttpStatus.OK)
    public List<CartItemResponse> retrieveAuthCartItem(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId) {
        return cartItemRetrieveService.retrieveAuthCartItem(authId);
    }

    /**
     * UPDATE
     **/
    @Operation(summary = "장바구니 상품 수정", description = "주어진 인증 ID, 이미지 파일 및 장바구니 상품 수정 요청을 사용하여 사용자 장바구니 상품을 수정합니다.")
    @PatchMapping("/carts/custom-products/{customProductId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyUserCartItem(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId, @RequestPart CartModifyRequestDto dto,
                                   @RequestPart(value = "file", required = false) MultipartFile imageFile, @PathVariable String customProductId) {
        cartItemModifyService.modifyUserCartItem(authId, dto, imageFile, customProductId);
    }

    /**
     * DELETE
     **/
    @Operation(summary = "장바구니 상품 삭제", description = "주어진 인증 ID 및 장바구니 상품 ID를 사용하여 사용자 장바구니 상품을 삭제합니다.")
    @DeleteMapping("/carts/custom-products/{customProductId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCartItem(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId, @PathVariable String customProductId) {
        cartItemRemoveService.removeCartItem(authId, customProductId);
    }

    @Operation(summary = "장바구니 상품 목록 삭제", description = "주어진 인증 ID 및 장바구니 상품 ID 목록을 사용하여 사용자 장바구니 상품 목록을 삭제합니다.")
    @DeleteMapping("/carts/custom-products")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCartItemList(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
                                   @RequestBody @Validated CartItemListRemoveRequestDto dto) {
        cartItemRemoveService.removeCartItemList(authId, dto);
    }

}
