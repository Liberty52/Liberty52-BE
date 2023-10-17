package com.liberty52.product.service.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liberty52.product.global.config.DBInitConfig;
import com.liberty52.product.service.applicationservice.ImageGenerationService;
import com.liberty52.product.service.applicationservice.ImageUpscalingService;
import com.liberty52.product.service.controller.dto.ImageGenerationDto;
import com.liberty52.product.service.controller.dto.ImageUpscalingDto;

@WebMvcTest(controllers = ImageController.class)
class ImageControllerTest {
	@InjectMocks
	ImageController controller;
	@MockBean
	ImageGenerationService imageGenerationService;
	@MockBean
	ImageUpscalingService imageUpscalingService;
	@Autowired
	MockMvc mockMvc;
	String authId = DBInitConfig.DBInitService.AUTH_ID;

	@Test
	void imageGenerate() throws Exception {
		String prompt = "A cute baby sea otter";
		int n = 2;
		ImageGenerationDto.Request request = new ImageGenerationDto.Request(prompt, n);
		String url1 = "url1";
		String url2 = "url2";
		BDDMockito.given(imageGenerationService.generateImage(authId, request))
			.willReturn(new ImageGenerationDto.Response(List.of(url1, url2)));

		mockMvc.perform(MockMvcRequestBuilders
				.post("/images/generations")
				.content(new ObjectMapper().writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, authId))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.urls.length()").value(2))
			.andExpect(jsonPath("$.urls[0]").value(url1))
			.andExpect(jsonPath("$.urls[1]").value(url2));
	}

	@Test
	void validationError() throws Exception {
		String prompt = "";
		int n = 1;
		ImageGenerationDto.Request request = new ImageGenerationDto.Request(prompt, n);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/images/generations")
				.content(new ObjectMapper().writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, authId))
			.andExpect(status().isBadRequest());
	}

	@Test
	void requiredHeaderError() throws Exception {
		int n = 1;
		ImageGenerationDto.Request request = new ImageGenerationDto.Request(UUID.randomUUID().toString(), n);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/images/generations")
				.content(new ObjectMapper().writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}

	@Test
	void tooLongPrompt() throws Exception {
		int n = 1;
		ImageGenerationDto.Request request = new ImageGenerationDto.Request("p".repeat(1001), n);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/images/generations")
				.content(new ObjectMapper().writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}

	@Test
	void success() throws Exception {
		ImageUpscalingDto.Response exp = new ImageUpscalingDto.Response(UUID.randomUUID().toString(),
			UUID.randomUUID().toString());
		given(imageUpscalingService.upscale(any(), anyInt()))
			.willReturn(exp);

		mockMvc.perform(
				post("/images/upscaling")
					.contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(new ImageUpscalingDto.Request(exp.beforeUrl(), 2)))
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isCreated())
			.andExpect(jsonPath("$.beforeUrl").value(exp.beforeUrl()))
			.andExpect(jsonPath("$.afterUrl").value(exp.afterUrl()));
	}
}