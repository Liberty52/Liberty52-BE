package com.liberty52.product.service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@Entity(name = "product_delivery_option")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDeliveryOption {

    @Id
    private final String id = UUID.randomUUID().toString();

    /** 택배사 이름 */
    @Column(name = "courier_name", nullable = false)
    private String courierName;

    /** 배송비 */
    @Column(name = "delivery_fee", nullable = false)
    private Integer fee;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public void associate(Product product) {
        this.product = product;
        this.product.setDeliveryOption(this);
    }
}
