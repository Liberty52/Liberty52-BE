package com.liberty52.product.service.controller;

import com.liberty52.authentication.annotation.LBPreAuthorize;
import com.liberty52.authentication.annotation.UserContext;
import com.liberty52.authentication.core.UserRole;
import com.liberty52.authentication.core.principal.User;
import com.liberty52.product.global.exception.external.internalservererror.InternalServerErrorException;
import com.liberty52.product.service.applicationservice.OrderCancelService;
import com.liberty52.product.service.applicationservice.OrderCreateService;
import com.liberty52.product.service.applicationservice.OrderDeliveryService;
import com.liberty52.product.service.applicationservice.OrderRetrieveService;
import com.liberty52.product.service.controller.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "주문", description = "주문 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderRetrieveService orderRetrieveService;
    private final OrderCancelService orderCancelService;
    private final OrderCreateService orderCreateService;
    private final OrderDeliveryService orderDeliveryService;

    // 주문 조회 관련
    @Operation(summary = "주문 목록 조회", description = "인증된 사용자의 주문 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<OrdersRetrieveResponse>> retrieveOrders(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        return ResponseEntity.ok(orderRetrieveService.retrieveOrders(authorization));
    }

    @Operation(summary = "주문 상세 조회", description = "인증된 사용자의 주문 상세 정보를 조회합니다.")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailRetrieveResponse> retrieveOrderDetail(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @PathVariable("orderId") String orderId) {
        return ResponseEntity.ok(orderRetrieveService.retrieveOrderDetail(authorization, orderId));
    }

    // 주문 취소 관련
    @Operation(summary = "주문 취소", description = "사용자가 주문을 취소하는 엔드포인트입니다.")
    @PostMapping("/cancel")
    @ResponseStatus(HttpStatus.OK)
    public OrderCancelDto.Response cancelOrder(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @RequestBody @Validated OrderCancelDto.Request request
    ) {
        return orderCancelService.cancelOrder(authId, request);
    }

    // 카드 결제 주문 생성 관련
    @Operation(summary = "카드 결제 주문 생성", description = "사용자가 카드 결제 주문을 생성하는 엔드포인트입니다.")
    @PostMapping("/card")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentCardResponseDto createCardPaymentOrders(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @RequestPart("dto") @Validated OrderCreateRequestDto dto,
            @RequestPart("imageFile") MultipartFile imageFile
    ) {
        return orderCreateService.createCardPaymentOrders(authId, dto, imageFile);
    }

    @Operation(summary = "카드 결제 주문 최종 승인 확인", description = "사용자가 카드 결제 주문의 최종 승인을 확인하는 엔드포인트입니다.")
    @GetMapping("/card/{orderId}/confirm")
    @ResponseStatus(HttpStatus.OK)
    public PaymentConfirmResponseDto confirmFinalApprovalOfCardPayment(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @PathVariable("orderId") String orderId
    ) {
        return orderCreateService.confirmFinalApprovalOfCardPayment(authId, orderId);
    }

    // 가상계좌 결제 주문 생성 관련
    @Operation(summary = "가상계좌 결제 주문 생성", description = "사용자가 가상계좌 결제 주문을 생성하는 엔드포인트입니다.")
    @PostMapping("/vbank")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentVBankResponseDto createVBankPaymentOrders(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @RequestPart("dto") @Validated OrderCreateRequestDto dto,
            @RequestPart("imageFile") MultipartFile imageFile
    ) {
        return orderCreateService.createVBankPaymentOrders(authId, dto, imageFile);
    }

    // 카트를 이용한 주문 생성 관련
    @Operation(summary = "카트를 이용한 카드 결제 주문 생성", description = "사용자가 카트를 이용하여 카드 결제 주문을 생성하는 엔드포인트입니다.")
    @PostMapping("/card/carts")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentCardResponseDto createCardPaymentOrdersByCarts(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @RequestBody @Validated OrderCreateRequestDto dto
    ) {
        return orderCreateService.createCardPaymentOrdersByCarts(authId, dto);
    }

    @Operation(summary = "카트를 이용한 가상 계좌 결제 주문 생성", description = "사용자가 카트를 이용하여 가상 계좌 결제 주문을 생성하는 엔드포인트입니다.")
    @PostMapping("/vbank/carts")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentVBankResponseDto createVBankPaymentOrdersByCarts(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @RequestBody @Validated OrderCreateRequestDto dto
    ) {
        return orderCreateService.createVBankPaymentOrdersByCarts(authId, dto);
    }

    @Operation(summary = "주문의 실시간배송정보 조회", description = "유저가 배송시작된 주문에 대한 실시간 배송정보를 조회할 수 있는 리다이렉션 URL을 반환합니다.")
    @GetMapping("/{orderId}/delivery")
    @ResponseStatus(HttpStatus.OK)
    @LBPreAuthorize(roles = {UserRole.USER, UserRole.ADMIN})
    public void getRealTimeDeliveryInfo(
            @UserContext User user,
            @PathVariable(value = "orderId") String orderId,
            @RequestParam(value = "courierCode", required = true) String courierCode,
            @RequestParam(value = "trackingNumber", required = true) String trackingNumber,
            HttpServletResponse response
    ) {
        try {
            response.sendRedirect(orderDeliveryService.getRealTimeDeliveryInfoRedirectUrl(user, orderId, courierCode, trackingNumber));
        } catch (IOException e) {
            throw new InternalServerErrorException("리다이렉션 I/O에 오류가 발생하였습니다");
        }
    }
}
