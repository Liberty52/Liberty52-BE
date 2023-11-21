package com.liberty52.auth.user.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ReviewerProfileResponse {
    private String authorName;
    private String authorProfileUrl;
}
