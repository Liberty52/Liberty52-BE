package com.liberty52.product.global.adapter.stablediffusion.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

public class TextToImageDto {
    public record Request(
        String key,
        Integer width,
        Integer height,
        Integer samples
    ) {
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response extends StableDiffusionResponse<List<String>> {
    }
}
