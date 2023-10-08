package com.liberty52.product.global.config;

import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfig {

	@Bean
    @Primary
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModules(
                new JavaTimeModule(),
                new ParameterNamesModule() // ParameterNamesModule은 기본 생성자가 없어도 Deserialize가 가능하도록 해주는 모듈
        );
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return mapper;
	}
}
