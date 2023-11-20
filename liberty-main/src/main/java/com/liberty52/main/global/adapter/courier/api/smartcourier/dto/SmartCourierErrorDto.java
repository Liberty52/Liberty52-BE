package com.liberty52.main.global.adapter.courier.api.smartcourier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmartCourierErrorDto {
    private String code;
    private String msg;
    private Boolean status;
}
