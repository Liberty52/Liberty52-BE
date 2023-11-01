package com.liberty52.product.service.controller;

import com.liberty.authentication.annotation.LBPreAuthorize;
import com.liberty52.product.global.exception.external.badrequest.BadRequestException;
import com.liberty52.product.service.applicationservice.OrderCancelService;
import com.liberty52.product.service.applicationservice.OrderDeliveryService;
import com.liberty52.product.service.applicationservice.OrderRetrieveService;
import com.liberty52.product.service.applicationservice.OrderStatusModifyService;
import com.liberty52.product.service.controller.dto.*;
import com.liberty52.product.service.entity.OrderStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "주문", description = "주문 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
public class OrderAdminController {

    private final OrderRetrieveService orderRetrieveService;
    private final OrderCancelService orderCancelService;
    private final OrderStatusModifyService orderStatusModifyService;
    private final OrderDeliveryService orderDeliveryService;

    // 주문 생성 관련
    @Operation(summary = "고객 환불 주문 생성", description = "관리자가 고객에 대한 환불 주문을 생성하는 엔드포인트입니다.")
    @PostMapping("/refund")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void refundCustomerOrderByAdmin(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String adminId,
            @RequestHeader("LB-Role") String role,
            @RequestBody @Validated OrderRefundDto.Request request
    ) {
        orderCancelService.refundCustomerOrderByAdmin(adminId, role, request);
    }

    // 주문 조회 관련
    @Operation(summary = "관리자 주문 목록 조회", description = "관리자의 주문 목록을 조회합니다.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public AdminOrderListResponse retrieveOrdersByAdmin(
            @RequestHeader("LB-Role") String role,
            Pageable pageable) {
        return orderRetrieveService.retrieveOrdersByAdmin(role, pageable);
    }

    @Operation(summary = "관리자 주문 상세 조회", description = "관리자의 주문 상세 정보를 조회합니다.")
    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDetailRetrieveResponse retrieveOrderDetailByAdmin(
            @RequestHeader("LB-Role") String role,
            @PathVariable String orderId) {
        return orderRetrieveService.retrieveOrderDetailByAdmin(role, orderId);
    }

    // 주문 취소 관련
    @Operation(summary = "관리자 취소된 주문 목록 조회", description = "관리자의 취소된 주문 목록을 조회합니다.")
    @GetMapping("/cancel")
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
    @GetMapping("/cancel/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public AdminCanceledOrderDetailResponse retrieveCanceledOrderDetailByAdmin(
            @RequestHeader("LB-Role") String role,
            @PathVariable String orderId) {
        return orderRetrieveService.retrieveCanceledOrderDetailByAdmin(role, orderId);
    }

    // 주문 상태 변경 관련
    @Operation(summary = "주문 상태 변경", description = "관리자가 주문의 상태를 수정합니다.")
    @PutMapping("/{orderId}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyOrderStatusByAdmin(
            @RequestHeader("LB-Role") String role,
            @PathVariable String orderId, @RequestParam OrderStatus orderStatus
    ) {
        orderStatusModifyService.modifyOrderStatusByAdmin(role, orderId, orderStatus);
    }

    @Operation(summary = "가상계좌 주문 상태 수정", description = "관리자가 가상계좌 주문의 상태를 수정합니다.")
    @PutMapping("/{orderId}/vbank")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyOrderStatusOfVBankByAdmin(
            @RequestHeader("LB-Role") String role, @PathVariable String orderId,
            @Validated @RequestBody VBankStatusModifyDto dto
    ) {
        orderStatusModifyService.modifyOrderStatusOfVBankByAdmin(role, orderId, dto);
    }

    @Operation(summary = "택배사 리스트 조회", description = "관리자가 택배사 리스트를 조회합니다.")
    @GetMapping("/courier-companies")
    @ResponseStatus(HttpStatus.OK)
    @LBPreAuthorize
    public AdminCourierListDto.Response getCourierCompanyList(
            @RequestParam(value = "international", defaultValue = "false", required = false) Boolean isInternational
    ) {
        return orderDeliveryService.getCourierCompanyList(isInternational);
    }

    @Operation(summary = "주문 택배 운송장번호 등록", description = "관리자가 배송시작되는 주문에 대하여 택배사 및 운송장번호를 등록합니다.")
    @PostMapping("/{orderId}/delivery")
    @ResponseStatus(HttpStatus.OK)
    @LBPreAuthorize
    public AdminAddOrderDeliveryDto.Response addOrderDeliveryByAdmin(
            @PathVariable(value = "orderId", required = true) String orderId,
            @Validated @RequestBody AdminAddOrderDeliveryDto.Request dto
    ) {
        return orderDeliveryService.add(orderId, dto);
    }
}

