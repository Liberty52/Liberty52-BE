package com.liberty52.product.global.adapter.courier.api.smartcourier;

import com.liberty52.product.global.adapter.courier.CourierCompanyClient;
import com.liberty52.product.global.adapter.courier.api.smartcourier.dto.SmartCourierCompanyListDto;
import com.liberty52.product.global.adapter.courier.api.smartcourier.dto.SmartCourierErrorDto;
import com.liberty52.product.global.adapter.courier.util.UriUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Slf4j
public class SmartCourierCompanyClient implements CourierCompanyClient<String, List<Map<String, Object>>> {

    private final String apiKey;
    private final WebClient webClient;

    public SmartCourierCompanyClient(String apiKey, WebClient webClient) {
        this.apiKey = apiKey;
        this.webClient = webClient;
    }

    private static final String PATH = "/api/v1/companylist";

    public Map<String, List<Map<String, Object>>> getCourierCompanyList() {
        var dto = webClient.get()
                .uri(uriBuilder -> UriUtil.uriWithApiKey(uriBuilder, PATH, "t_key", apiKey))
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
}
