package com.liberty52.main.global.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class BeanConfig {

    //Dto<->Entity Mapper
    @Bean
    @Primary
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    //Custom Mapper : 일부 필드 수정 전용
    @Bean("modelMapperPatch")
    public ModelMapper modelMapperPatch() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true).setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return modelMapper;
    }
}
