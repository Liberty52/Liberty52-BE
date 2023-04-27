package com.liberty52.product;

import com.liberty52.product.global.adapter.cloud.AuthServiceClient;
import com.liberty52.product.stub.StubAuthServiceClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestBeanConfig {

    @Bean
    public AuthServiceClient authServiceClient() {
        System.out.println("Create Mock Bean Auth Service Client");
        return new StubAuthServiceClient();
    }

}
