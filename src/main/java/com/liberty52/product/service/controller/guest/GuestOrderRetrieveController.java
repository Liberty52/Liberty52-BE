package com.liberty52.product.service.controller.guest;

import com.liberty52.product.service.applicationservice.OrderRetrieveService;
import com.liberty52.product.service.controller.dto.OrderDetailRetrieveResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "주문", description = "주문 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class GuestOrderRetrieveController {

    private final OrderRetrieveService orderRetrieveService;

    @Operation(summary = "비회원을 주문 상세 조회", description = "비회원을 주문의 상세 정보를 조회합니다.")
    @GetMapping("/guest/orders/{orderNumber}")
    public ResponseEntity<OrderDetailRetrieveResponse> retrieveGuestOrderDetail(
        @RequestHeader(HttpHeaders.AUTHORIZATION) String guestId,
        @PathVariable("orderNumber") String orderNumber){
        return ResponseEntity.ok(orderRetrieveService.retrieveGuestOrderDetail(guestId,orderNumber));
    }

}
