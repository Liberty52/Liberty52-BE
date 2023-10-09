package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.OrderStatusModifyService;
import com.liberty52.product.service.controller.dto.VBankStatusModifyDto;
import com.liberty52.product.service.entity.OrderStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "주문", description = "주문 관련 API를 제공합니다")
@RequiredArgsConstructor
@RestController
public class OrderStatusModifyController {

  private final OrderStatusModifyService orderStatusModifyService;

  @Operation(summary = "주문 상태 변경", description = "관리자가 주문의 상태를 수정합니다.")
  @PutMapping("/admin/orders/{orderId}/status")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void modifyOrderStatusByAdmin(
          @RequestHeader("LB-Role") String role,
          @PathVariable String orderId, @RequestParam OrderStatus orderStatus
  ) {
    orderStatusModifyService.modifyOrderStatusByAdmin(role, orderId, orderStatus);
  }

  @Operation(summary = "가상계좌 주문 상태 수정", description = "관리자가 가상계좌 주문의 상태를 수정합니다.")
  @PutMapping("/admin/orders/{orderId}/vbank")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void modifyOrderStatusOfVBankByAdmin(
          @RequestHeader("LB-Role") String role, @PathVariable String orderId,
          @Validated @RequestBody VBankStatusModifyDto dto
  ) {
    orderStatusModifyService.modifyOrderStatusOfVBankByAdmin(role, orderId, dto);
  }
}
