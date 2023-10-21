package com.liberty52.product.service.controller.dto;

import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class CartModifyRequestDto {

  private List<String> optionDetailIds;

  @Min(1)
  private int quantity;

  public static CartModifyRequestDto create(List<String> optionDetailIds,int quantity){
    return new CartModifyRequestDto(optionDetailIds,quantity);
  }
}
