package com.liberty52.main;

import com.liberty52.main.global.adapter.cloud.AuthServiceClient;
import com.liberty52.main.stub.StubAuthServiceClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestBeanConfig {

    @Bean
    public AuthServiceClient authServiceClient() {
        return new StubAuthServiceClient();
    }

}
