package com.liberty52.main.service.controller.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class ReviewCreateRequestDto {
    @NotEmpty
    String customProductId;
    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;
    @NotEmpty
    @Size(min = 1, max = 1000)
    private String content;

    public static ReviewCreateRequestDto createForTest(Integer rating, String content, String customProductId) {
        ReviewCreateRequestDto dto = new ReviewCreateRequestDto();
        dto.rating = rating;
        dto.content = content;
        dto.customProductId = customProductId;
        return dto;
    }
}
