package com.liberty52.product.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.liberty52.product.service.applicationservice.OrderCancelService;
import com.liberty52.product.service.controller.dto.OrderCancelDto;
import com.liberty52.product.service.controller.dto.OrderRefundDto;

import lombok.RequiredArgsConstructor;

@Tag(name = "주문", description = "주문 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class OrderCancelController {

    private final OrderCancelService orderCancelService;

    @Operation(summary = "주문 취소", description = "사용자가 주문을 취소하는 엔드포인트입니다.")
    @PostMapping("/orders/cancel")
    @ResponseStatus(HttpStatus.OK)
    public OrderCancelDto.Response cancelOrder(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @RequestBody @Validated OrderCancelDto.Request request
    ) {
        return orderCancelService.cancelOrder(authId, request);
    }

    @Operation(summary = "고객 환불 주문 생성", description = "관리자가 고객에 대한 환불 주문을 생성하는 엔드포인트입니다.")
    @PostMapping("/admin/orders/refund")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void refundCustomerOrderByAdmin(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String adminId,
            @RequestHeader("LB-Role") String role,
            @RequestBody @Validated OrderRefundDto.Request request
    ) {
        orderCancelService.refundCustomerOrderByAdmin(adminId, role, request);
    }
}
