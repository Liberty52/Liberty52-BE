package com.liberty52.main.global.adapter.portone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortOneWebhookDto {

    private String imp_uid;
    private String merchant_uid;
    private String status;

    public static PortOneWebhookDto testOf(String merchantUid, String status) {
        return new PortOneWebhookDto("IMP_TEST", merchantUid, status);
    }

}
