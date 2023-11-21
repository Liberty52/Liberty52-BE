package com.liberty52.main.global.adapter.courier.config;

import com.liberty52.main.global.adapter.courier.CourierCompanyClient;
import com.liberty52.main.global.adapter.courier.api.smartcourier.SmartCourierCompanyClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SmartCourierConfig {

    private final WebClient smartCourierClient;
    @Value("${smart-courier.api-key}")
    private String smartCourierApiKey;

    public SmartCourierConfig(
            @Qualifier(value = "smartCourierClient") WebClient smartCourierClient
    ) {
        this.smartCourierClient = smartCourierClient;
    }

    @Bean
    @SuppressWarnings("rawtypes")
    public CourierCompanyClient courierCompanyClient() {
        return new SmartCourierCompanyClient(smartCourierApiKey, smartCourierClient);
    }
}
