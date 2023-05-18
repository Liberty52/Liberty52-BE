package com.liberty52.product.service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OrderCancelRequestDto {

    @NotBlank
    private String orderId;
    @NotBlank
    private String reason;

    // case vbank
    private String refundBank;
    private String refundHolder;
    private String refundAccount;
    private String refundPhoneNum;

}
