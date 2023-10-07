package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.OrderCancelService;
import com.liberty52.product.service.controller.dto.OrderCancelDto;
import com.liberty52.product.service.controller.dto.OrderRefundDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "주문", description = "주문 관련 API를 제공합니다")
public class OrderCancelController {

    private final OrderCancelService orderCancelService;

    @PostMapping("/orders/cancel")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "주문 취소", description = "사용자가 주문을 취소합니다.")
    public OrderCancelDto.Response cancelOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
                                               @RequestBody @Validated OrderCancelDto.Request request) {
        return orderCancelService.cancelOrder(authId, request);
    }

    @PostMapping("/admin/orders/refund")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "주문 환불", description = "관리자가 고객의 주문을 환불합니다.")
    public void refundCustomerOrderByAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String adminId,
                                    @RequestHeader("LB-Role") String role,
                                    @RequestBody @Validated OrderRefundDto.Request request) {
        orderCancelService.refundCustomerOrderByAdmin(adminId, role, request);
    }

}
