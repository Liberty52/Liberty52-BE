package com.liberty52.main.service.entity.license;

import com.liberty52.main.service.controller.dto.LicenseOptionDetailModifyDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "license_option_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LicenseOptionDetail {
    @Id
    private final String id = UUID.randomUUID().toString();
    @ManyToOne
    @JoinColumn(name = "license_option_id")
    private LicenseOption licenseOption;

    @Column(nullable = false)
    private String artName;
    @Column(nullable = false)
    private String artistName;
    @Column(nullable = false)
    private Integer stock;
    @Column(nullable = false)
    private Boolean onSale;
    @Column(nullable = false)
    private String artUrl;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;

    @Builder
    private LicenseOptionDetail(String artName, String artistName, Integer stock, Boolean onSale, String artUrl,
                                Integer price, LocalDate startDate, LocalDate endDate) {
        this.artName = artName;
        this.artistName = artistName;
        this.stock = stock;
        this.onSale = onSale;
        this.artUrl = artUrl;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static LicenseOptionDetail create(String artName, String artistName, Integer stock, Boolean onSale,
                                             String artUrl, Integer price, LocalDate startDate, LocalDate endDate) {
        return builder().artName(artName).artistName(artistName).stock(stock).onSale(onSale).artUrl(artUrl)
                .price(price).startDate(startDate).endDate(endDate).build();
    }

    public void associate(LicenseOption licenseOption) {
        this.licenseOption = licenseOption;
        this.licenseOption.addDetail(this);
    }

    public void modifyLicenseOptionDetail(LicenseOptionDetailModifyDto dto) {
        this.artName = dto.getArtName();
        this.artistName = dto.getArtistName();
        this.stock = dto.getStock();
        this.onSale = dto.getOnSale();
        this.price = dto.getPrice();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
    }

    public void modifyLicenseArtUrl(String artUrl) {
        this.artUrl = artUrl;
    }

    public void updateOnSale() {
        onSale = !onSale;
    }

    public Optional<LicenseOptionDetail> sold(int quantity) {
        synchronized (this) {
            int stockAfterSold = stock - quantity;
            if (!onSale || stock == 0 || stockAfterSold < 0) {
                return Optional.empty();
            }
            stock = stockAfterSold;
            return Optional.of(this);
        }
    }

    public void rollbackStock(int quantity) {
        synchronized (this) {
            this.stock += quantity;
        }
    }

}
