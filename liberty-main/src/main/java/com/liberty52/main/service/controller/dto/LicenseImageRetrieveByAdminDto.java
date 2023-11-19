package com.liberty52.main.service.controller.dto;

import com.liberty52.main.service.entity.LicenseImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class LicenseImageRetrieveByAdminDto {
    private String id;
    private String artistName;
    private String artName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String licenseImageUrl;
    private Integer stock;

    public LicenseImageRetrieveByAdminDto(LicenseImage licenseImage) {
        this.id = licenseImage.getId();
        this.artistName = licenseImage.getArtistName();
        this.artName = licenseImage.getArtName();
        this.startDate = licenseImage.getStartDate();
        this.endDate = licenseImage.getEndDate();
        this.licenseImageUrl = licenseImage.getLicenseImageUrl();
        this.stock = licenseImage.getStock();
    }
}
