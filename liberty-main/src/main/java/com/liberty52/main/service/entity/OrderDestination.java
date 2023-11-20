package com.liberty52.main.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderDestination {

    @Id
    private final String id = UUID.randomUUID().toString();

    private String receiverName;
    private String receiverEmail;
    private String receiverPhoneNumber;
    private String address1;
    private String address2;
    private String zipCode;

    @OneToOne(mappedBy = "orderDestination")
    private Orders orders;

    private OrderDestination(String receiverName, String receiverEmail, String receiverPhoneNumber,
                             String address1, String address2, String zipCode) {
        this.receiverName = receiverName;
        this.receiverEmail = receiverEmail;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.address1 = address1;
        this.address2 = address2;
        this.zipCode = zipCode;
    }

    public static OrderDestination create(String receiverName, String receiverEmail, String receiverPhoneNumber,
                                          String address1, String address2, String zipCode) {
        return new OrderDestination(receiverName, receiverEmail, receiverPhoneNumber, address1, address2, zipCode);
    }
}
