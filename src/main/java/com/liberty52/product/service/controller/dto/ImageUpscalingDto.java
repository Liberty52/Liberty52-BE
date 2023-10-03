package com.liberty52.product.service.controller.dto;

public class ImageUpscalingDto {
    public record Request(
        String url,
        Integer scale
    ) {
    }

    public record Response(
        String beforeUrl,
        String afterUrl
    ) {
    }
}
