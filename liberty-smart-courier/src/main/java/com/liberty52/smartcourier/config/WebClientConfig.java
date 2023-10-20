package com.liberty52.smartcourier.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean(value = "smartCourierClient")
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://info.sweettracker.co.kr")
                .defaultHeaders(it -> {
                    it.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
                    it.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
                })
                .build();
    }
}
