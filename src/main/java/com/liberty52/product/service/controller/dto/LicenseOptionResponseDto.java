package com.liberty52.product.service.controller.dto;

import java.time.LocalDate;
import java.util.List;

import com.liberty52.product.service.entity.license.LicenseOption;
import com.liberty52.product.service.entity.license.LicenseOptionDetail;

import lombok.Getter;

@Getter
public class LicenseOptionResponseDto {

    private String id;
    private String name;
    private List<LicenseOptionDetailDto> optionItems;

    public LicenseOptionResponseDto(LicenseOption licenseOption) {
        this.id = licenseOption.getId();
        this.name = licenseOption.getName();

        this.optionItems = licenseOption.getLicenseOptionDetails().stream()
                .filter(LicenseOptionDetail::getOnSale)
                .map(LicenseOptionDetailDto::new).toList();

    }

    @Getter
    public class LicenseOptionDetailDto {
        private String id;
        private String artName;
        private String artistName;
        private Integer stock;
        private String artUrl;
        private Integer price;
        private LocalDate startDate;
        private LocalDate endDate;

        public LicenseOptionDetailDto(LicenseOptionDetail detail) {
            this.id = detail.getId();
            this.artName = detail.getArtName();
            this.artistName = detail.getArtistName();
            this.stock = detail.getStock();
            this.artUrl = detail.getArtUrl();
            this.price = detail.getPrice();
            this.startDate = detail.getStartDate();
            this.endDate = detail.getEndDate();
        }
    }
}
