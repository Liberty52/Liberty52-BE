package com.liberty52.product.service.entity.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liberty52.product.service.entity.Orders;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue(value = "CARD")
public class CardPayment extends Payment<CardPayment.CardPaymentInfo> {

    public CardPayment() {
        super();
        this.type = PaymentType.CARD;
    }

    public static CardPayment of(Orders orders) {
        CardPayment cardPayment = new CardPayment();
        cardPayment.orders = orders;
        orders.setPayment(cardPayment);
        return cardPayment;
    }

    @Override
    public <T extends PaymentInfo> void setInfo(T dto) {
        try {
            if (!(dto instanceof CardPaymentInfo)) {
                throw new RuntimeException(); // throw new InfoTypeInvalidException;
            }
            this.info = new ObjectMapper().writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CardPayment.CardPaymentInfo getInfoAsDto() {
        try {
            return new ObjectMapper().readValue(this.info, CardPaymentInfo.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getInfoAsString() {
        return this.info;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CardPaymentInfo extends PaymentInfo {
        private String impUid;
        private String pgProvider;
        private String pgTid;
        private LocalDateTime paidAt;
        private String cardName;
        private String cardNumber;
        private Integer cardQuota;
        public static CardPaymentInfo of(String impUid, String pgProvider, String pgTid, LocalDateTime paidAt, String cardName, String cardNumber, Integer cardQuota) {
            return new CardPaymentInfo(impUid, pgProvider, pgTid, paidAt, cardName, cardNumber, cardQuota);
        }
    }

}
