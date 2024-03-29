package com.liberty52.main.service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Cart {
    @Id
    private final String id = UUID.randomUUID().toString();

    @Column(unique = true, nullable = false, updatable = false)
    private String authId;

    private LocalDate expiryDate;

    @OneToMany(mappedBy = "cart")
    private final List<CustomProduct> customProducts = new ArrayList<>();

    private Cart(String authId) {
        this.authId = authId;
    }

    public static Cart create(String authId) {
        return new Cart(authId);
    }

    public void addCustomProduct(CustomProduct customProduct) {
        customProducts.add(customProduct);
    }

    public void updateExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

}
