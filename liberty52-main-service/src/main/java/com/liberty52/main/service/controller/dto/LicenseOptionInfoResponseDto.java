package com.liberty52.main.service.controller.dto;

import com.liberty52.main.service.entity.license.LicenseOption;
import com.liberty52.main.service.entity.license.LicenseOptionDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LicenseOptionInfoResponseDto {

    String licenseOptionId;
    String optionName;
    List<LicenseOptionDetailInfoResponseDto> licenseOptionDetailList;

    public LicenseOptionInfoResponseDto(LicenseOption licenseOption, boolean onSale) {
        this.licenseOptionId = licenseOption.getId();
        this.optionName = licenseOption.getName();

        if (licenseOption.getLicenseOptionDetails().isEmpty()) {
            licenseOptionDetailList = Collections.emptyList();
        }

        if (onSale) {
            licenseOptionDetailList = licenseOption.getLicenseOptionDetails()
                    .stream()
                    .filter(LicenseOptionDetail::getOnSale)
                    .map(LicenseOptionDetailInfoResponseDto::new)
                    .toList();
        }
        licenseOptionDetailList = licenseOption.getLicenseOptionDetails()
                .stream()
                .sorted(Comparator.comparing(LicenseOptionDetail::getOnSale).reversed())
                .map(LicenseOptionDetailInfoResponseDto::new)
                .toList();
    }

}
