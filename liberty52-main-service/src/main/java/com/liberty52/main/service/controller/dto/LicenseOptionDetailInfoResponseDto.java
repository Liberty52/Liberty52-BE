package com.liberty52.main.service.controller.dto;

import com.liberty52.main.service.entity.license.LicenseOptionDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LicenseOptionDetailInfoResponseDto {

    String licenseOptionDetailId;
    String artName;
    String artistName;
    Integer stock;
    Boolean onSale;
    String artUrl;
    Integer price;
    LocalDate startDate;
    LocalDate endDate;

    public LicenseOptionDetailInfoResponseDto(LicenseOptionDetail licenseOptionDetail) {
        licenseOptionDetailId = licenseOptionDetail.getId();
        artName = licenseOptionDetail.getArtName();
        artistName = licenseOptionDetail.getArtistName();
        stock = licenseOptionDetail.getStock();
        price = licenseOptionDetail.getPrice();
        onSale = licenseOptionDetail.getOnSale();
        artUrl = licenseOptionDetail.getArtUrl();
        startDate = licenseOptionDetail.getStartDate();
        endDate = licenseOptionDetail.getEndDate();
    }

}
