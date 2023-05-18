package com.liberty52.product.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CanceledOrders {

    @Id
    private String id = UUID.randomUUID().toString();
    private String reason;
    private LocalDateTime reqAt = LocalDateTime.now();
    private LocalDateTime canceledAt;
    private int fee;
    @OneToOne
    private Orders orders;

    private CanceledOrders(String reason) {
        this.reason = reason;
    }

    public static CanceledOrders of(String reason, Orders order) {
        CanceledOrders canceledOrders = new CanceledOrders(reason);
        canceledOrders.associate(order);
        order.changeOrderStatusToCancelRequest();
        return canceledOrders;
    }

    public void associate(Orders order) {
        this.orders = order;
    }

    public void approveCanceled(int fee) {
        this.canceledAt = LocalDateTime.now();
        this.fee = fee;
    }

}
