package com.liberty52.product.service.utils;

import com.liberty52.product.global.adapter.AuthClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

public class TestConfig {

    @Profile("test")
    @Bean
    public AuthClient authClient(){
        return new MockAuthClient();
    }

}
