package com.liberty52.product.service.controller.dto;

import com.liberty52.product.service.entity.payment.VBank;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class VBankDto {
    private String vBankId;
    private String bankOfVBank;
    private String account;
    private String holder;
    private String vBank;

    public static VBankDto fromEntity(VBank vBank) {
        return VBankDto.builder()
                .vBankId(vBank.getId())
                .bankOfVBank(vBank.getBank().getKoName())
                .account(vBank.getAccount())
                .holder(vBank.getHolder())
                .vBank(vBank.getBank().getKoName() + " " + vBank.getAccount() + " " + vBank.getHolder())
                .build();
    }
}