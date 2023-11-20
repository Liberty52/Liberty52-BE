package com.liberty52.auth.service.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ModifyResponseDto {

  private String profileUrl;
  private String email;
  private String name;
  private String phoneNumber;
}
