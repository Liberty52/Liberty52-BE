package com.liberty52.product.service.controller.dto;

import com.liberty52.product.service.entity.payment.VBank;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VBankInfoListResponseDto {

    private List<VBankInfoDto> vbankInfos;

    public static VBankInfoListResponseDto of(List<VBankInfoDto> vbankInfos) {
        return new VBankInfoListResponseDto(vbankInfos);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class VBankInfoDto {
        private String bankOfVBank;
        private String account;
        private String holder;
        private String vBank;

        public static VBankInfoDto fromEntity(VBank vBank) {
            return VBankInfoDto.builder()
                    .bankOfVBank(vBank.getBank().getKoName())
                    .account(vBank.getAccount())
                    .holder(vBank.getHolder())
                    .vBank(vBank.getBank().getKoName() + " " + vBank.getAccount() + " " + vBank.getHolder())
                    .build();
        }
    }

}
