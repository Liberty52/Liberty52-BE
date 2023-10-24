package com.liberty52.product.service.applicationservice.impl;

import com.liberty52.product.global.adapter.ImageUpscaler;
import com.liberty52.product.global.adapter.stablediffusion.StableDiffusionClient;
import com.liberty52.product.global.exception.external.internalservererror.InternalServerErrorException;
import com.liberty52.product.global.util.Result;
import com.liberty52.product.service.controller.dto.ImageUpscalingDto;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ImageUpscalingServiceImplTest {
    private final ImageUpscaler imageUpscaler = mock(StableDiffusionClient.class);

    private final ImageUpscalingServiceImpl service = new ImageUpscalingServiceImpl(imageUpscaler);

    @Test
    void success() {
        String exp = UUID.randomUUID().toString();
        given(imageUpscaler.upscale(any(), any()))
            .willReturn(Mono.just(exp));
        ImageUpscalingDto.Response act = service.upscale(UUID.randomUUID().toString(), 2);
        assertEquals(exp, act.afterUrl());
    }

    @Test
    void failure() {
        given(imageUpscaler.upscale(any(), any()))
            .willReturn(Mono.error(new RuntimeException()));
        assertThrows(InternalServerErrorException.class, () -> service.upscale(UUID.randomUUID().toString(), 2));
    }
}
