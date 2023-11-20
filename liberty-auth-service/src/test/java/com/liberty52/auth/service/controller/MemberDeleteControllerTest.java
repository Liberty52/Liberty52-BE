package com.liberty52.auth.service.controller;

import com.liberty52.auth.global.config.WebSecurityConfig;
import com.liberty52.auth.service.applicationservice.MemberDeleteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {MemberDeleteController.class},
    excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class)},
    excludeAutoConfiguration = {SecurityAutoConfiguration.class,
        SecurityFilterAutoConfiguration.class,
        OAuth2ClientAutoConfiguration.class,
        OAuth2ResourceServerAutoConfiguration.class}
)
class MemberDeleteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    MemberDeleteController memberDeleteController;

    @MockBean
    MemberDeleteService memberDeleteService;

    final String DELETE = "/member";


    @Test
    void getTokenFromGateway() throws Exception{
        //given
        final String token = UUID.randomUUID().toString();
        //when
        mockMvc.perform(delete(DELETE)
                        .header(HttpHeaders.AUTHORIZATION,
                                token))
                //then
                .andExpect(status().isOk())
                .andDo(print());
    }



}
