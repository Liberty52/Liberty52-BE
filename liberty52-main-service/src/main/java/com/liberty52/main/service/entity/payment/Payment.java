package com.liberty52.main.service.entity.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.liberty52.main.service.entity.Orders;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Payment<T extends Payment.PaymentInfo> {

    protected static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

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
    @JoinColumn(name = "order_id", nullable = false, updatable = false)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    protected Orders orders;
    @Column(length = 1000)
    protected String info = "";

    public static Payment<? extends PaymentInfo> cardOf() {
        return CardPayment.of();
    }

    public static Payment<? extends PaymentInfo> vbankOf() {
        return VBankPayment.of();
    }

    public static Payment<? extends PaymentInfo> naverPayOf() {
        return null;
    }

    public void associate(Orders orders) {
        this.orders = orders;
        this.orders.setPayment(this);
    }

    public void changeStatusToPaid() {
        this.status = PaymentStatus.PAID;
    }

    public void changeStatusToForgery() {
        this.status = PaymentStatus.FORGERY;
    }

    public void changeStatusToRefund() {
        this.status = PaymentStatus.REFUND;
    }

    public abstract <T extends PaymentInfo> void setInfo(T dto);

    public abstract <T extends PaymentInfo> T getInfoAsDto();

    public abstract String getInfoAsString();

    public static class PaymentInfo {
    }

}
