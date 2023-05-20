package com.liberty52.product.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liberty52.product.global.config.DBInitConfig;
import com.liberty52.product.service.applicationservice.ImageGenerationService;
import com.liberty52.product.service.controller.dto.ImageGenerationDto;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ImageGenerationController.class)
class ImageGenerationControllerTest {
    @InjectMocks
    ImageGenerationController controller;
    @MockBean
    ImageGenerationService service;
    @Autowired
    MockMvc mockMvc;
    String authId = DBInitConfig.DBInitService.AUTH_ID;

    @Test
    void imageGenerate() throws Exception {
        String prompt = "A cute baby sea otter";
        ImageGenerationDto.Request request = new ImageGenerationDto.Request(prompt);
        String returnUrl = "url1";
        BDDMockito.given(service.generate(authId, request))
                .willReturn(new ImageGenerationDto.Response(List.of(returnUrl)));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/images/generations")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, authId))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.urls.length()").value(1))
                .andExpect(jsonPath("$.urls[0]").value(returnUrl));
    }

    @Test
    void validationError() throws Exception {
        String prompt = "";
        ImageGenerationDto.Request request = new ImageGenerationDto.Request(prompt);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/images/generations")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, authId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void requiredHeaderError() throws Exception {
        ImageGenerationDto.Request request = new ImageGenerationDto.Request(UUID.randomUUID().toString());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/images/generations")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void tooLongPrompt() throws Exception {
        ImageGenerationDto.Request request = new ImageGenerationDto.Request("p".repeat(1001));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/images/generations")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }
}