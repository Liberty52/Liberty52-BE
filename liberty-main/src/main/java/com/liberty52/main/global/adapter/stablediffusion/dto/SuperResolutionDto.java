package com.liberty52.main.global.adapter.stablediffusion.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

public class SuperResolutionDto {
    @Builder
    public record Request(
            String key,
            String url,
            Integer scale,
            String model_id,
            String webhook,
            Boolean face_enhancer
    ) {
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response extends StableDiffusionResponse<String> {
    }
}
