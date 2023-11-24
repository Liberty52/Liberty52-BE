package com.liberty52.main.service.controller.guest;

import com.liberty52.main.service.applicationservice.CartItemCreateService;
import com.liberty52.main.service.applicationservice.CartItemModifyService;
import com.liberty52.main.service.applicationservice.CartItemRemoveService;
import com.liberty52.main.service.applicationservice.CartItemRetrieveService;
import com.liberty52.main.service.controller.dto.CartItemListRemoveRequestDto;
import com.liberty52.main.service.controller.dto.CartItemRequest;
import com.liberty52.main.service.controller.dto.CartItemResponse;
import com.liberty52.main.service.controller.dto.CartModifyRequestDto;
import com.liberty52.product.service.controller.dto.CartItemRequestWithLicense;
import com.liberty52.product.service.controller.dto.CartModifyWithLicenseRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "장바구니", description = "장바구니 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class CartItemGuestController {

    private final CartItemCreateService cartItemCreateService;
    private final CartItemRetrieveService cartItemRetrieveService;
    private final CartItemModifyService cartItemModifyService;
    private final CartItemRemoveService cartItemRemoveService;


    /**
     * CREATE
     **/
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

    /**
     * CREATE
     **/
    @Operation(summary = "비회원 장바구니에 라이선스 상품 추가", description = "비회원 장바구니에 상품을 추가합니다.")
    @PostMapping("/guest/carts/license-products")
    @ResponseStatus(HttpStatus.CREATED)
    public void createGuestCartItem(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String guestId,
            @RequestPart CartItemRequestWithLicense dto
    ) {
        cartItemCreateService.createGuestCartItemWithLicense(guestId, dto);
    }

    /**
     * READ
     **/
    @Operation(summary = "비회원 장바구니 조회", description = "비회원의 장바구니에 있는 상품 목록을 조회합니다.")
    @GetMapping("/guest/carts")
    @ResponseStatus(HttpStatus.OK)
    public List<CartItemResponse> retrieveGuestCartItem(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String guestId
    ) {
        return cartItemRetrieveService.retrieveGuestCartItem(guestId);
    }

    /**
     * UPDATE
     **/
    @Operation(summary = "비회원 장바구니 상품 수정", description = "비회원 장바구니에 있는 상품을 수정합니다.")
    @PatchMapping("/guest/carts/custom-products/{customProductId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyGuestCartItem(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String guestId,
            @RequestPart CartModifyRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile imageFile,
            @PathVariable String customProductId
    ) {
        cartItemModifyService.modifyGuestCartItem(guestId, dto, imageFile, customProductId);
    }

    /**
     * UPDATE
     **/
    @Operation(summary = "비회원 장바구니 상품 수정", description = "비회원 장바구니에 있는 상품을 수정합니다.")
    @PatchMapping("/guest/carts/custom-products/{customProductId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyGuestCartItemWithLicense(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String guestId,
            @RequestPart CartModifyWithLicenseRequestDto dto,
            @PathVariable String customProductId
    ) {
        cartItemModifyService.modifyGuestCartItemWithLicense(guestId, dto, customProductId);
    }

    /**
     * DELETE
     **/
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
