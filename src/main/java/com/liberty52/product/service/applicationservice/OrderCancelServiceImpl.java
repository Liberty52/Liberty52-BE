package com.liberty52.product.service.applicationservice;

import com.liberty52.product.global.adapter.portone.PortOneService;
import com.liberty52.product.global.exception.external.badrequest.CannotOrderCancelException;
import com.liberty52.product.global.exception.external.forbidden.NotYourOrderException;
import com.liberty52.product.global.exception.external.notfound.OrderNotFoundByIdException;
import com.liberty52.product.service.controller.dto.OrderCancelRequestDto;
import com.liberty52.product.service.entity.CanceledOrders;
import com.liberty52.product.service.entity.OrderStatus;
import com.liberty52.product.service.entity.Orders;
import com.liberty52.product.service.entity.payment.PaymentType;
import com.liberty52.product.service.repository.CanceledOrdersRepository;
import com.liberty52.product.service.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderCancelServiceImpl implements OrderCancelService {

    private final PortOneService portOneService;
    private final OrdersRepository ordersRepository;
    private final CanceledOrdersRepository canceledOrdersRepository;

    @Override
    public void cancelOrder(String authId, OrderCancelRequestDto dto) {
        Orders order = ordersRepository.findById(dto.getOrderId())
                .map(orders -> {
                    if (!Objects.equals(orders.getAuthId(), authId)) {
                        throw new NotYourOrderException(authId);
                    }
                    return orders;
                })
                .orElseThrow(() -> new OrderNotFoundByIdException(dto.getOrderId()));

        // validate already canceled
        if (order.getOrderStatus() == OrderStatus.CANCELED) {

        }

        boolean isCanceledDirect = switch (order.getOrderStatus()) {
            case WAITING_DEPOSIT, ORDERED -> true;
            case MAKING -> false;
            default -> throw new CannotOrderCancelException(order.getOrderStatus());
        };

        if (order.getPayment().getType() == PaymentType.CARD) {
            cancelCardOrder(order, dto, isCanceledDirect);
        } else {
            cancelVbankOrder(order, dto, isCanceledDirect);
        }

        // save canceled orders

    }

    private void cancelCardOrder(Orders order, OrderCancelRequestDto dto, boolean isCanceledDirect) {
        portOneService.requestCancelPayment(dto.getOrderId(), dto.getReason());
        CanceledOrders canceledOrders = CanceledOrders.of(dto.getReason(), order);
        if (isCanceledDirect) {
            canceledOrders.approveCanceled(0);
        }
    }

    private void cancelVbankOrder(Orders order, OrderCancelRequestDto dto, boolean isCanceledDirect) {
        CanceledOrders canceledOrders = CanceledOrders.of(dto.getReason(), order);
        if (isCanceledDirect) {
            canceledOrders.approveCanceled(0);
        }
    }
}
