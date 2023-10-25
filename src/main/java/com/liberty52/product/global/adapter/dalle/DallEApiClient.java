package com.liberty52.product.global.adapter.dalle;

import com.liberty52.product.global.adapter.TextToImageGenerator;
import com.liberty52.product.global.adapter.dalle.dto.DallEDto;
import com.liberty52.product.global.adapter.dalle.error.DallEError;
import com.liberty52.product.global.adapter.s3.S3Uploader;
import com.liberty52.product.global.util.Result;
import com.liberty52.product.global.util.Retries;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.awt.*;
import java.time.Duration;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DallEApiClient implements TextToImageGenerator {
    @Value("${openai.api-key}")
    private String apiKey;
    private WebClient webClient;
    private static final String BASE_URL = "https://api.openai.com/v1";
    private static final String PATH_GENERATION = "/images/generations";

    private final S3Uploader s3Uploader;

    @PostConstruct
    private void init() {
        this.webClient = WebClient.builder()
            .baseUrl(BASE_URL)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .exchangeStrategies( // b64 로 받을 때 메모리 제한에 걸리는 것에 대한 해결. limit 없애버림.
                ExchangeStrategies.builder()
                    .codecs(config -> config.defaultCodecs().maxInMemorySize(-1))
                    .build())
            .build();
    }

    @Override
    public Mono<List<String>> generate(String prompt, Dimension dim, Integer n) {
        var size = switch (dim.width) {
            case 256 -> DallEDto.Request.Size.S256;
            case 516 -> DallEDto.Request.Size.S512;
            default -> DallEDto.Request.Size.S1024;
        };
        var request = new DallEDto.Request(prompt, n, size, DallEDto.Request.Format.B64_JSON, "unknown");
        return Result.runCatching(
                () -> webClient.post()
                    .uri(PATH_GENERATION)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(DallEDto.Response.B64Json.class)
                    .map(d -> d.getData().stream()
                        .map(DallEDto.Response.Data.B64Json::getB64Json)
                        .map(Base64::decodeBase64)
                        .map(s3Uploader::uploadOfByte)
                        .toList()
                    )
            ).recoverCatching(DallEApiClient::toErrorMono)
            .getOrThrow()
            .retryWhen(Retries.withDefault(log));
    }

    private static <T> Mono<T> toErrorMono(Throwable throwable) {
        return Mono.error(new DallEError.ErrorOccurredBeforeRequest(throwable.getMessage(), throwable.getCause()));
    }
}
