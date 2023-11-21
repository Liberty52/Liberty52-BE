package com.liberty52.main.global.adapter.stablediffusion;

import com.liberty52.main.global.adapter.ImageUpscaler;
import com.liberty52.main.global.adapter.TextToImageGenerator;
import com.liberty52.main.global.adapter.stablediffusion.dto.StableDiffusionResponse;
import com.liberty52.main.global.adapter.stablediffusion.dto.SuperResolutionDto;
import com.liberty52.main.global.adapter.stablediffusion.dto.TextToImageDto;
import com.liberty52.main.global.adapter.stablediffusion.error.StableDiffusionError;
import com.liberty52.main.global.util.Result;
import com.liberty52.main.global.util.Retries;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.List;

@Component
@Slf4j
public class StableDiffusionClient implements ImageUpscaler, TextToImageGenerator {
    @Value("${stable-diffusion.key}")
    private String API_KEY;

    @Value("${stable-diffusion.url.base}")
    private String BASE_URL;

    @Value("${stable-diffusion.url.super-resolution}")
    private String SUPER_RESOLUTION;

    @Value("${stable-diffusion.url.text-to-image}")
    private String TEXT_TO_IMAGE;

    private WebClient webClient;

    private static <O> Mono<O> convertResponseToOutput(StableDiffusionResponse<O> response) {
        if (!"success".equals(response.getStatus())) {
            return Mono.error(StableDiffusionError.ResponseBodyStatusIsNotSuccess::new);
        }
        return Mono.just(response.getOutput());
    }

    private static <T> Mono<T> toErrorMono(Throwable throwable) {
        return Mono.error(new StableDiffusionError.ErrorOccurredBeforeRequest(throwable.getMessage(), throwable.getCause()));
    }

    @PostConstruct
    private void init() {
        webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public Mono<List<String>> generate(String prompt, Dimension size, Integer n) {
        return request(
                HttpMethod.POST,
                TEXT_TO_IMAGE,
                new TextToImageDto.Request(API_KEY, size.width, size.height, n),
                TextToImageDto.Response.class
        );
    }

    @Override
    public Mono<String> upscale(String srcUri, Scale scale) {
        return request(
                HttpMethod.POST,
                SUPER_RESOLUTION,
                SuperResolutionDto.Request.builder()
                        .key(API_KEY)
                        .url(srcUri)
                        .scale(((Scale.Mul) scale).getWidth().intValue())
                        .build(),
                SuperResolutionDto.Response.class
        );
    }

    private <I, O, R extends StableDiffusionResponse<O>> Mono<O> request(HttpMethod method, String uri, I requestBody, Class<R> responseClass) {
        return Result.runCatching(
                        () -> webClient.method(method)
                                .uri(uri)
                                .body(BodyInserters.fromValue(requestBody))
                                .retrieve()
                                .bodyToMono(responseClass)
                                .flatMap(StableDiffusionClient::convertResponseToOutput)
                ).recoverCatching(StableDiffusionClient::toErrorMono)
                .getOrThrow()
                .retryWhen(Retries.withDefault(log));
    }
}
