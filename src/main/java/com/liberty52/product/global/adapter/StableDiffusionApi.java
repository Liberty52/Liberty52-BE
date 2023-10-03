package com.liberty52.product.global.adapter;

import com.liberty52.product.global.util.Result;
import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Component
@Slf4j
public class StableDiffusionApi implements ImageUpscaler {
    @Value("${stable-diffusion.key}")
    private String API_KEY;

    @Value("${stable-diffusion.url.base}")
    private String BASE_URL;

    @Value("${stable-diffusion.url.super-resolution}")
    private String SUPER_RESOLUTION;

    private WebClient webClient;

    @PostConstruct
    private void init() {
        webClient = WebClient.builder()
            .baseUrl(BASE_URL)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }


    @Override
    public Result<String> upscale(String srcUri, Scale scale) {
        System.out.println(BASE_URL);
        return Result.runCatching(
                () -> webClient.post()
                    .uri(SUPER_RESOLUTION)
                    .body(BodyInserters.fromValue(
                        Dto.Request.builder()
                            .key(API_KEY)
                            .url(srcUri)
                            .scale(((Scale.Mul) scale).getWidth().intValue())
                            .build())
                    ).retrieve()
                    .bodyToMono(Dto.Response.class)
                    .block()
            ).onSuccess(body -> log.info("{}/upscaling success. body={}.", this.getClass().getSimpleName(), body.toString()))
            .mapCatching(body -> Objects.requireNonNull(body).output)
            .onFailure(err -> log.error("{}/upscaling. Error: {}.", this.getClass().getSimpleName(), err.getMessage()));
    }

    public static class Dto {
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

        public record Response(
            String status,
            Double generationTime,
            Long id,
            String output
        ) {
        }
    }
}
