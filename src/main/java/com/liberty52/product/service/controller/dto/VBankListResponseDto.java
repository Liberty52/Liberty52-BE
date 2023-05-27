package com.liberty52.product.service.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VBankListResponseDto {

    private List<VBankDto> vbankInfos;

    public static VBankListResponseDto of(List<VBankDto> vbankInfos) {
        return new VBankListResponseDto(vbankInfos);
    }

}
