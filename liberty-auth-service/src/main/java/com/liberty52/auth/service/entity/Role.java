package com.liberty52.auth.service.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

  GUEST("GUEST"), USER("USER"), ADMIN("ADMIN");

  private final String key;
}

