package com.liberty52.main.service.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class LicenseOptionResponse {
    String optionId;
    String optionName;
    String detailId;
    String detailName;
    int price;

    public static LicenseOptionResponse of(String optionId, String optionName, String detailId, String detailName, Integer price) {
        return new LicenseOptionResponse(optionId, optionName, detailId, detailName, price);
    }
}
