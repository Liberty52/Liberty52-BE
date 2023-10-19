package com.liberty52.product.service.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.liberty52.product.service.applicationservice.ProductCreateService;
import com.liberty52.product.service.controller.dto.ProductRequestDto;
import com.liberty52.product.service.entity.ProductState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductCreateService productCreateService;

    @Test
    void 상품추가_성공() throws Exception {
        //given
        MockMultipartFile image = new MockMultipartFile("image", "testImg.png", "image/png", "test image content".getBytes());

        ProductRequestDto request = ProductRequestDto.builder()
                .name("testName")
                .price(100L)
                .productState(ProductState.ON_SALE)
                .isCustom(true)
                .build();
        String jsonData = objectMapper.writeValueAsString(request);

        MockMultipartFile data = new MockMultipartFile("data","data","application/json",jsonData.getBytes());
        //when
        mockMvc.perform(
                multipart("/admin/product")
                        .file(image)
                        .file(data)
                        .header("LB-Role","ADMIN")
                )
                //then
                .andExpect(status().isCreated());
    }

    @Test
    void 상품추가_잘못된데이터요청() throws Exception {
        //given
        MockMultipartFile image = new MockMultipartFile("image", "testImg.png", "image/png", "test image content".getBytes());

        Map<String, Object> request = new HashMap<>();
        request.put("name", "TestProduct");
        request.put("price", 100);
        /*isCustom 미입력 가정*/
        request.put("productState","ON_SALE");
        String jsonData = objectMapper.writeValueAsString(request);

        MockMultipartFile data = new MockMultipartFile("data","data","application/json",jsonData.getBytes());
        //when
        mockMvc.perform(
                multipart("/admin/product")
                        .file(image)
                        .file(data)
                        .header("LB-Role","ADMIN")
                )
                //then
                .andExpect(status().isBadRequest());
    }
}
