package com.liberty52.main.service.controller.dto;

import com.liberty52.main.service.entity.LicenseImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LicenseImageRetrieveDto {
    private String imageId;
    private String artistName;
    private String artName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String imageUrl;
    private Integer stock;

    public LicenseImageRetrieveDto(LicenseImage licenseImage) {
        this.imageId = licenseImage.getId();
        this.artistName = licenseImage.getArtistName();
        this.artName = licenseImage.getArtName();
        this.startDate = licenseImage.getStartDate();
        this.endDate = licenseImage.getEndDate();
        this.imageUrl = licenseImage.getLicenseImageUrl();
        this.stock = licenseImage.getStock();
    }
}
