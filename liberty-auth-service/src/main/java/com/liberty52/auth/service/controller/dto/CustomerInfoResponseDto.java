package com.liberty52.auth.service.controller.dto;

import com.liberty52.auth.service.entity.Auth;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CustomerInfoResponseDto {
    private String id;
    private String name;
    private String email;
    private String profileUrl;
    private String phoneNumber;
    private LocalDate createdAt;

    public static CustomerInfoResponseDto of(Auth auth) {
        return builder()
                .id(auth.getId())
                .name(auth.getName())
                .email(auth.getEmail())
                .profileUrl(auth.getProfileUrl())
                .phoneNumber(auth.getPhoneNumber())
                .createdAt(auth.getCreatedAt())
                .build();
    }

    public static CustomerInfoResponseDto of(String id, String name, String phoneNumber, LocalDate createdAt, String role) {
        return builder()
                .id(id)
                .name(name)
                .phoneNumber(phoneNumber)
                .createdAt(createdAt)
                .build();
    }
}
