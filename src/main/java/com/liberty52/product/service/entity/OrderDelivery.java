package com.liberty52.product.service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/** 주문배송정보 */
@Entity(name = "order_delivery")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDelivery {

    @Id
    private final String id = UUID.randomUUID().toString();

    /** 택배사 코드 */
    @Column(name = "courier_company_code", nullable = false)
    private String courierCompanyCode;

    /** 택배사 이름 */
    @Column(name = "courier_company_name", nullable = false)
    private String courierCompanyName;

    /** 운송장 번호 */
    @Column(name = "tracking_number", nullable = false)
    private String trackingNumber;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    public static OrderDelivery of(String courierCompanyCode, String courierCompanyName, String trackingNumber, Orders order) {
        var instance = OrderDelivery.builder()
                .courierCompanyCode(courierCompanyCode)
                .courierCompanyName(courierCompanyName)
                .trackingNumber(trackingNumber)
                .build();
        instance.associate(order);
        return instance;
    }

    public OrderDelivery update(String courierCompanyCode, String courierCompanyName, String trackingNumber) {
        this.courierCompanyCode = courierCompanyCode;
        this.courierCompanyName = courierCompanyName;
        this.trackingNumber = trackingNumber;
        return this;
    }

    public void associate(Orders order) {
        this.order = order;
        order.setOrderDelivery(this);
    }

    public Boolean isMatch(String courierCompanyCode, String trackingNumber) {
        return this.courierCompanyCode.equals(courierCompanyCode) && this.trackingNumber.equals(trackingNumber);
    }

    public Boolean isNotMatch(String courierCompanyCode, String trackingNumber) {
        return !isMatch(courierCompanyCode, trackingNumber);
    }
}
