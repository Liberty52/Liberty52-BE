package com.liberty52.smartcourier.api.dto;

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
