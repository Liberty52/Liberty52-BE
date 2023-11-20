package com.liberty52.auth.service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyRequestDto {
  @NotBlank
  private String originPassword;

  private String updatePassword;
  @NotBlank
  private String phoneNumber;
  @NotBlank
  private String name;
}
