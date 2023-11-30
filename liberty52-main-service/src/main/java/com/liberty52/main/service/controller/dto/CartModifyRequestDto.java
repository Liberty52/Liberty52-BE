package com.liberty52.main.service.controller.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CartModifyRequestDto {

    private List<String> optionDetailIds;

    @Min(1)
    private int quantity;

    public static CartModifyRequestDto create(List<String> optionDetailIds, int quantity) {
        return new CartModifyRequestDto(optionDetailIds, quantity);
    }
}
