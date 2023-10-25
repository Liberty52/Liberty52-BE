package com.liberty52.product.global.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    //Dto<->Entity Mapper
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
