package com.liberty52.product.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.liberty52.product.global.exception.external.badrequest.BadRequestException;
import com.liberty52.product.service.applicationservice.OrderRetrieveService;
import com.liberty52.product.service.controller.dto.*;

import java.util.List;

@Tag(name = "주문", description = "주문 관련 API를 제공합니다")
@RequiredArgsConstructor
@RestController
public class OrderRetrieveController {

    private final OrderRetrieveService orderRetrieveService;

    @Operation(summary = "주문 목록 조회", description = "인증된 사용자의 주문 목록을 조회합니다.")
    @GetMapping("/orders")
    public ResponseEntity<List<OrdersRetrieveResponse>> retrieveOrders(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        return ResponseEntity.ok(orderRetrieveService.retrieveOrders(authorization));
    }

    @Operation(summary = "주문 상세 조회", description = "인증된 사용자의 주문 상세 정보를 조회합니다.")
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderDetailRetrieveResponse> retrieveOrderDetail(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @PathVariable("orderId") String orderId) {
        return ResponseEntity.ok(orderRetrieveService.retrieveOrderDetail(authorization, orderId));
    }

    @Operation(summary = "관리자 주문 목록 조회", description = "관리자의 주문 목록을 조회합니다.")
    @GetMapping("/admin/orders")
    @ResponseStatus(HttpStatus.OK)
    public AdminOrderListResponse retrieveOrdersByAdmin(
            @RequestHeader("LB-Role") String role,
            Pageable pageable) {
        return orderRetrieveService.retrieveOrdersByAdmin(role, pageable);
    }

    @Operation(summary = "관리자 주문 상세 조회", description = "관리자의 주문 상세 정보를 조회합니다.")
    @GetMapping("/admin/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDetailRetrieveResponse retrieveOrderDetailByAdmin(
            @RequestHeader("LB-Role") String role,
            @PathVariable String orderId) {
        return orderRetrieveService.retrieveOrderDetailByAdmin(role, orderId);
    }

    @Operation(summary = "관리자 취소된 주문 목록 조회", description = "관리자의 취소된 주문 목록을 조회합니다.")
    @GetMapping("/admin/orders/cancel")
    @ResponseStatus(HttpStatus.OK)
    public AdminCanceledOrderListResponse retrieveCanceledOrdersByAdmin(
            @RequestHeader("LB-Role") String role,
            Pageable pageable,
            @RequestParam(value = "type", required = false) String type) {
        if (type == null || type.isBlank()) {
            return orderRetrieveService.retrieveCanceledOrdersByAdmin(role, pageable);
        } else {
            return switch (type) {
                case "REQUESTED" -> orderRetrieveService.retrieveOnlyRequestedCanceledOrdersByAdmin(role, pageable);
                case "CANCELED" -> orderRetrieveService.retrieveOnlyCanceledOrdersByAdmin(role, pageable);
                default -> throw new BadRequestException("type 파라미터가 유효하지 않습니다.");
            };
        }
    }

    @Operation(summary = "관리자 취소된 주문 상세 조회", description = "관리자의 취소된 주문 상세 정보를 조회합니다.")
    @GetMapping("/admin/orders/cancel/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public AdminCanceledOrderDetailResponse retrieveCanceledOrderDetailByAdmin(
            @RequestHeader("LB-Role") String role,
            @PathVariable String orderId) {
        return orderRetrieveService.retrieveCanceledOrderDetailByAdmin(role, orderId);
    }
}
