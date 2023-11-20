package com.liberty52.main.service.controller.dto;

import lombok.Data;

@Data
public class AuthClientDataResponse {
    private String authorName;
    private String authorProfileUrl;

    public AuthClientDataResponse(String authorName, String authorProfileUrl) {
        this.authorName = authorName;
        this.authorProfileUrl = authorProfileUrl;
    }
}
