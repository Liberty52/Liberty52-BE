package com.liberty52.main.service.controller.guest;

import com.liberty52.authentication.annotation.LBPreAuthorize;
import com.liberty52.authentication.annotation.UserContext;
import com.liberty52.authentication.core.UserRole;
import com.liberty52.authentication.core.principal.User;
import com.liberty52.main.global.exception.external.internalservererror.InternalServerErrorException;
import com.liberty52.main.service.applicationservice.OrderCreateService;
import com.liberty52.main.service.applicationservice.OrderDeliveryService;
import com.liberty52.main.service.applicationservice.OrderRetrieveService;
import com.liberty52.main.service.controller.dto.*;
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

@Tag(name = "주문", description = "주문 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class OrderGuestController {

    private final OrderCreateService orderCreateService;
    private final OrderRetrieveService orderRetrieveService;
    private final OrderDeliveryService orderDeliveryService;

    /**
     * CREATE
     **/
    @Operation(summary = "카드 결제 주문 생성", description = "비회원을 위한 카드 결제 주문을 생성합니다.")
    @PostMapping("/guest/orders/card")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentCardResponseDto preregisterCardPaymentOrdersByGuest(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String guestId,
            @RequestPart("dto") @Validated OrderCreateRequestDto dto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ) {
        return orderCreateService.createCardPaymentOrders(guestId, dto, imageFile);
    }

    @Operation(summary = "카드 결제 주문 확정", description = "비회원을 위한 카드 결제 주문을 확정합니다.")
    @GetMapping("/guest/orders/card/{orderId}/confirm")
    @ResponseStatus(HttpStatus.OK)
    public PaymentConfirmResponseDto confirmFinalApprovalOfCardPaymentByGuest(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String guestId,
            @PathVariable("orderId") String orderId
    ) {
        return orderCreateService.confirmFinalApprovalOfCardPayment(guestId, orderId);
    }

    @Operation(summary = "가상계좌 결제 주문 생성", description = "비회원을 위한 가상계좌 결제 주문을 생성합니다.")
    @PostMapping("/guest/orders/vbank")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentVBankResponseDto registerVBankPaymentOrdersByGuest(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String guestId,
            @RequestPart("dto") @Validated OrderCreateRequestDto dto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ) {
        return orderCreateService.createVBankPaymentOrders(guestId, dto, imageFile);
    }

    @Operation(summary = "카트를 이용한 카드 결제 주문 생성", description = "비회원을 위한 카트를 이용한 카드 결제 주문을 생성합니다.")
    @PostMapping("/guest/orders/card/carts")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentCardResponseDto createCardPaymentOrdersByCarts(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String guestId,
            @RequestBody @Validated OrderCreateRequestDto dto
    ) {
        return orderCreateService.createCardPaymentOrdersByCartsForGuest(guestId, dto);
    }

    @Operation(summary = "카트를 이용한 가상계좌 결제 주문 생성", description = "비회원을 위한 카트를 이용한 가상계좌 결제 주문을 생성합니다.")
    @PostMapping("/guest/orders/vbank/carts")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentVBankResponseDto createVBankPaymentOrdersByCarts(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String guestId,
            @RequestBody @Validated OrderCreateRequestDto dto
    ) {
        return orderCreateService.createVBankPaymentOrdersByCartsForGuest(guestId, dto);
    }

    /**
     * READ
     **/
    @Operation(summary = "비회원을 주문 상세 조회", description = "비회원을 주문의 상세 정보를 조회합니다.")
    @GetMapping("/guest/orders/{orderNumber}")
    public ResponseEntity<OrderDetailRetrieveResponse> retrieveGuestOrderDetail(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String guestId,
            @PathVariable("orderNumber") String orderNumber) {
        return ResponseEntity.ok(orderRetrieveService.retrieveGuestOrderDetail(guestId, orderNumber));
    }

    @Operation(summary = "비회원 유저 주문의 실시간배송정보 조회", description = "비회원 유저가 배송시작된 주문에 대한 실시간 배송정보를 조회할 수 있는 리다이렉션 URL을 반환합니다.")
    @GetMapping("/guest/orders/{orderNumber}/delivery")
    @ResponseStatus(HttpStatus.OK)
    @LBPreAuthorize(role = UserRole.GUEST)
    public void getGuestRealTimeDeliveryInfo(
            @UserContext User guest,
            @PathVariable(value = "orderNumber") String orderNumber,
            @RequestParam(value = "courierCode", required = true) String courierCode,
            @RequestParam(value = "trackingNumber", required = true) String trackingNumber,
            HttpServletResponse response
    ) {
        try {
            response.sendRedirect(orderDeliveryService.getGuestRealTimeDeliveryInfoRedirectUrl(guest, orderNumber, courierCode, trackingNumber));
        } catch (IOException e) {
            throw new InternalServerErrorException("리다이렉션 I/O에 오류가 발생하였습니다");
        }
    }
}
