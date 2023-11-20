package com.liberty52.main.service.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveProductOptionRequestDto {

    @NotNull
    Boolean onSale;

    public static RetrieveProductOptionRequestDto of(boolean onSale) {
        return new RetrieveProductOptionRequestDto(onSale);
    }
}
