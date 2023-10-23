package com.liberty52.product.global.adapter.courier.config;

import com.liberty52.product.global.adapter.courier.CourierCompanyClient;
import com.liberty52.product.global.adapter.courier.api.smartcourier.SmartCourierCompanyClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SmartCourierConfig {

    @Value("${smart-courier.api-key}")
    private String smartCourierApiKey;

    private final WebClient smartCourierClient;

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
