package com.liberty52.main.service.controller.dto;

import com.liberty52.main.service.entity.license.LicenseOption;
import com.liberty52.main.service.entity.license.LicenseOptionDetail;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class LicenseOptionResponseDto {

    private final String id;
    private final String name;
    private final List<LicenseOptionDetailDto> optionItems;

    public LicenseOptionResponseDto(LicenseOption licenseOption) {
        this.id = licenseOption.getId();
        this.name = licenseOption.getName();

        this.optionItems = licenseOption.getLicenseOptionDetails().stream()
                .filter(LicenseOptionDetail::getOnSale)
                .map(LicenseOptionDetailDto::new).toList();

    }

    @Getter
    public class LicenseOptionDetailDto {
        private final String id;
        private final String artName;
        private final String artistName;
        private final Integer stock;
        private final String artUrl;
        private final Integer price;
        private final LocalDate startDate;
        private final LocalDate endDate;

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
