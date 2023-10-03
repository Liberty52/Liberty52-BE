package com.liberty52.product.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liberty52.product.service.applicationservice.ImageUpscalingService;
import com.liberty52.product.service.controller.dto.ImageUpscalingDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImageUpscalingController.class)
class ImageUpscalingControllerTest {
    @MockBean
    private ImageUpscalingService service;

    @Autowired
    private MockMvc mvc;

    @Test
    void success() throws Exception {
        ImageUpscalingDto.Response exp = new ImageUpscalingDto.Response(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        given(service.upscale(any(), anyInt()))
            .willReturn(exp);

        mvc.perform(
                post("/images/upscaling")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(new ImageUpscalingDto.Request(exp.beforeUrl(), 2)))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated())
            .andExpect(jsonPath("$.beforeUrl").value(exp.beforeUrl()))
            .andExpect(jsonPath("$.afterUrl").value(exp.afterUrl()));
    }
}
