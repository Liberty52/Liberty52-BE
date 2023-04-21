package com.liberty52.product.service.entity.payment;

import com.liberty52.product.service.entity.Orders;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Payment<T extends Payment.PaymentInfo> {

    @Id
    protected String id = UUID.randomUUID().toString();

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    protected PaymentType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected PaymentStatus status = PaymentStatus.READY;

    @Column(updatable = false, nullable = false)
    protected LocalDateTime reqAt = LocalDateTime.now();

    @JoinColumn(name = "order_id", nullable = false)
    @OneToOne(mappedBy = "payment")
    protected Orders orders;

    protected String info = "";

    public static Payment<? extends PaymentInfo> cardOf(Orders orders) {
        return CardPayment.of(orders);
    }

    public static Payment<? extends PaymentInfo> vbankOf(Orders orders) {
        return null;
    }

    public static Payment<? extends PaymentInfo> naverPayOf(Orders orders){
        return null;
    }

//    protected static Payment<? extends PaymentInfo> of(PaymentType type, Orders orders) {
//        Payment<? extends PaymentInfo> payment = new Payment<>();
//        payment.type = type;
//        payment.orders = orders;
//        orders.setPayment(payment);
//        return payment;
//    }

    protected void associate(Orders orders) {
        this.orders = orders;
        this.orders.setPayment(this);
    }

    public abstract <T extends PaymentInfo> void setInfo(T dto);
    public abstract <T extends PaymentInfo> T getInfoAsDto();
    public abstract String getInfoAsString();

    protected static class PaymentInfo {}

}
