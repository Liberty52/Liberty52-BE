package com.liberty52.product.service.entity.payment;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "idx__account", columnList = "account")
})
public class VBank {

    @Id
    private final String id = UUID.randomUUID().toString();
    @Enumerated(EnumType.STRING)
    private BankType bankOfVBank;
    private String account;
    private String holder;

    private VBank(String account) {
        this.account = account;
    }

    public static VBank of(String account) {
        return new VBank(account);
    }

}
