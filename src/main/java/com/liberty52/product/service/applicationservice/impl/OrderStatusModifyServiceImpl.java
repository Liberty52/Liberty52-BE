package com.liberty52.product.service.applicationservice.impl;

import com.liberty52.product.global.exception.external.badrequest.BadRequestException;
import com.liberty52.product.global.exception.external.notfound.OrderNotFoundByIdException;
import com.liberty52.product.global.util.Validator;
import com.liberty52.product.service.applicationservice.OrderStatusModifyService;
import com.liberty52.product.service.controller.dto.VBankStatusModifyDto;
import com.liberty52.product.service.entity.OrderStatus;
import com.liberty52.product.service.entity.Orders;
import com.liberty52.product.service.entity.payment.Payment;
import com.liberty52.product.service.entity.payment.VBankPayment;
import com.liberty52.product.service.entity.payment.VBankPayment.VBankPaymentInfo;
import com.liberty52.product.service.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class OrderStatusModifyServiceImpl implements OrderStatusModifyService {
  private final OrdersRepository ordersRepository;
  @Override
  public void modifyOrderStatusByAdmin(String role, String orderId, OrderStatus requestedStatus) {
    Validator.isAdmin(role);
    Orders order = ordersRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundByIdException(orderId));

    OrderStatus currentStatus = order.getOrderStatus();
    OrderStatus previousStatus = OrderStatus.getPreviousStatus(requestedStatus);

    if (currentStatus == requestedStatus) {
      throw new BadRequestException("현재와 같은 상태 변경입니다.");
    }
    if (currentStatus != previousStatus) {
      throw new BadRequestException("상태 변경이 허용되지 않습니다.");
    }
    order.modifyOrderStatus(requestedStatus);
  }

  @Override
  public void modifyOrderStatusOfVBank(String role, String orderId, VBankStatusModifyDto dto) {
    Validator.isAdmin(role);
    Orders order = ordersRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundByIdException(orderId));

    if (order.getOrderStatus() != OrderStatus.WAITING_DEPOSIT) {
      throw new BadRequestException("입금대기 상태가 아닙니다.");
    }

    VBankPayment.VBankPaymentInfo prev = order.getPayment().getInfoAsDto();
    VBankPaymentInfo vBank = VBankPaymentInfo.ofPaid(prev, dto);
    Payment<?> payment = order.getPayment();
    payment.setInfo(vBank);
    order.changeOrderStatusToOrdered();
  }
}