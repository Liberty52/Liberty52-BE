package com.liberty52.main.global.adapter.courier.api.smartcourier;

import com.liberty52.main.global.adapter.courier.CourierCompanyClient;
import com.liberty52.main.global.adapter.courier.api.smartcourier.dto.SmartCourierCompanyListDto;
import com.liberty52.main.global.adapter.courier.api.smartcourier.dto.SmartCourierErrorDto;
import com.liberty52.main.global.adapter.courier.config.CourierUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Slf4j
public class SmartCourierCompanyClient implements CourierCompanyClient {

    private final String apiKey;
    private final WebClient webClient;

    public SmartCourierCompanyClient(String apiKey, WebClient webClient) {
        this.apiKey = apiKey;
        this.webClient = webClient;
    }

    @SuppressWarnings("unchecked")
    public Map<String, List<Map<String, Object>>> getCourierCompanyList() {
        var dto = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/companylist")
                        .queryParam("t_key", apiKey)
                        .build())
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        errorResponse -> {
                            var error = errorResponse.bodyToMono(SmartCourierErrorDto.class).block(Duration.ofSeconds(1));
                            log.error("스마트택배API ERROR - {}", error);
                            return null;
                        }
                )
                .bodyToMono(SmartCourierCompanyListDto.class)
                .block();
        if (dto == null) {
            return null;
        }
        return dto.asMap();
    }

    @Override
    public String getDeliveryInfoRedirectUrl(String courierCode, String trackingNumber) {
        return UriComponentsBuilder.fromUriString(CourierUrl.BASE_URL + "/tracking/4")
                .queryParam("t_key", apiKey)
                .queryParam("t_code", courierCode)
                .queryParam("t_invoice", trackingNumber)
                .build().toUri().toString();
    }
}
