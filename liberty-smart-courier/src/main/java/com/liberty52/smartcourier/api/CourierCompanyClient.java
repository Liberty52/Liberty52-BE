package com.liberty52.smartcourier.api;

import com.liberty52.smartcourier.api.dto.CourierCompanyListDto;
import com.liberty52.smartcourier.api.dto.SmartCourierErrorDto;
import com.liberty52.smartcourier.util.UriUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Slf4j
@Component
public class CourierCompanyClient {

    private final String apiKey;

    private final WebClient webClient;

    public CourierCompanyClient(
            @Value("${smart-courier.api-key}") String apiKey,
            @Qualifier(value = "smartCourierClient") WebClient webClient
    ) {
        this.apiKey = apiKey;
        this.webClient = webClient;
    }

    private static final String PATH = "/api/v1/companylist";

    public CourierCompanyListDto getCourierCompanyList() {
        return webClient.get()
                .uri(uriBuilder -> UriUtil.uriWithApiKey(uriBuilder, PATH, apiKey))
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        errorResponse -> {
                            var error = errorResponse.bodyToMono(SmartCourierErrorDto.class).block(Duration.ofSeconds(1));
                            log.error("스마트택배API ERROR - {}", error);
                            return null;
                        }
                )
                .bodyToMono(CourierCompanyListDto.class)
                .block();
    }

}
