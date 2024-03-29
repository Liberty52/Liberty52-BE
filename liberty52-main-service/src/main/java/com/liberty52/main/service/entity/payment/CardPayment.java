package com.liberty52.main.service.entity.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.liberty52.main.global.adapter.portone.dto.PortOnePaymentInfo;
import com.liberty52.main.global.util.Utils;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue(value = "CARD")
public class CardPayment extends Payment<CardPayment.CardPaymentInfo> {

    public CardPayment() {
        super();
        this.type = PaymentType.CARD;
    }

    public static CardPayment of() {
        return new CardPayment();
    }

    @Override
    public <T extends PaymentInfo> void setInfo(T dto) {
        try {
            if (!(dto instanceof CardPaymentInfo)) {
                return;
            }
            this.info = objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SuppressWarnings({"JpaAttributeTypeInspection", "unchecked"})
    public CardPayment.CardPaymentInfo getInfoAsDto() {
        try {
            return objectMapper.readValue(this.info, CardPaymentInfo.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getInfoAsString() {
        return this.info;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CardPaymentInfo extends PaymentInfo {
        @JsonIgnoreProperties(allowSetters = true)
        private String impUid;
        @JsonIgnoreProperties(allowSetters = true)
        private String pgProvider;
        @JsonIgnoreProperties(allowSetters = true)
        private String pgTid;
        private LocalDateTime paidAt;
        private String cardName;
        private String cardNumber;
        private Integer cardQuota;

        public static CardPaymentInfo of(String impUid, String pgProvider, String pgTid, LocalDateTime paidAt, String cardName, String cardNumber, Integer cardQuota) {
            return new CardPaymentInfo(impUid, pgProvider, pgTid, paidAt, cardName, cardNumber, cardQuota);
        }

        public static CardPaymentInfo of(PortOnePaymentInfo dto) {
            CardPaymentInfo cardPaymentInfo = new CardPaymentInfo();
            cardPaymentInfo.impUid = dto.getImp_uid();
            cardPaymentInfo.pgProvider = dto.getPg_provider();
            cardPaymentInfo.pgTid = dto.getPg_tid();
            cardPaymentInfo.paidAt = Utils.convertUnixToLocalDateTime(dto.getPaid_at());
            cardPaymentInfo.cardName = dto.getCard_name();
            cardPaymentInfo.cardNumber = dto.getCard_number();
            cardPaymentInfo.cardQuota = dto.getCard_quota();
            return cardPaymentInfo;
        }
    }

}
