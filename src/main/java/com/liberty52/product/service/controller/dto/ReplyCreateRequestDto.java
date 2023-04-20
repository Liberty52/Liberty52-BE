package com.liberty52.product.service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReplyCreateRequestDto {
  @NotBlank
  private String content;
  public static ReplyCreateRequestDto createForTest(String content) {
    ReplyCreateRequestDto dto = new ReplyCreateRequestDto();
    dto.content = content;
    return dto;
  }
}
