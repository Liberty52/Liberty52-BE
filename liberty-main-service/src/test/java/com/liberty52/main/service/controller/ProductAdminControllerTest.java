package com.liberty52.main.service.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.liberty52.main.service.applicationservice.ProductCreateService;
import com.liberty52.main.service.applicationservice.ProductModifyService;
import com.liberty52.main.service.applicationservice.ProductRemoveService;
import com.liberty52.main.service.controller.dto.ProductCreateRequestDto;
import com.liberty52.main.service.entity.ProductState;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductAdminController.class)
public class ProductAdminControllerTest {
    final String testProductId = "LIB-001";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ModelMapper modelMapper;
    @MockBean
    private ProductCreateService productCreateService;
    @MockBean
    private ProductModifyService productModifyService;
    @MockBean
    private ProductRemoveService productRemoveService;

    @Test
    void 상품추가_성공() throws Exception {
        //given
        MockMultipartFile image = new MockMultipartFile("image", "testImg.png", "image/png", "test image content".getBytes());

        ProductCreateRequestDto requestData = ProductCreateRequestDto.builder()
                .name("testName")
                .price(100L)
                .productState(ProductState.ON_SALE)
                .isCustom(true)
                .build();
        String jsonData = objectMapper.writeValueAsString(requestData);

        MockMultipartFile data = new MockMultipartFile("data", "data", "application/json", jsonData.getBytes());
        //when
        mockMvc.perform(
                        multipart("/admin/product")
                                .file(image)
                                .file(data)
                                .header("LB-Role", "ADMIN")
                )
                //then
                .andExpect(status().isCreated());
    }

    @Test
    void 상품추가_실패_요청데이터누락() throws Exception {
        //given
        MockMultipartFile image = new MockMultipartFile("image", "testImg.png", "image/png", "test image content".getBytes());

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("name", "TestProduct");
        requestData.put("price", 100);
        /*isCustom 미입력 가정*/
        requestData.put("productState", "ON_SALE");
        String jsonData = objectMapper.writeValueAsString(requestData);

        MockMultipartFile data = new MockMultipartFile("data", "data", "application/json", jsonData.getBytes());
        //when
        mockMvc.perform(
                        multipart("/admin/product")
                                .file(image)
                                .file(data)
                                .header("LB-Role", "ADMIN")
                )
                //then
                .andExpect(status().isBadRequest());
    }

    @Test
    void 상품수정_성공_모든데이터전송() throws Exception {
        //given
        MockMultipartFile image = new MockMultipartFile("image", "testImg.png", "image/png", "test image content".getBytes());

        ProductCreateRequestDto requestData = ProductCreateRequestDto.builder()
                .name("testName")
                .price(100L)
                .productState(ProductState.ON_SALE)
                .isCustom(true)
                .build();
        String jsonData = objectMapper.writeValueAsString(requestData);

        MockMultipartFile data = new MockMultipartFile("data", "data", "application/json", jsonData.getBytes());
        //when
        mockMvc.perform(
                        multipart("/admin/product/" + testProductId)
                                .file(image)
                                .file(data)
                                .header("LB-Role", "ADMIN")
                                .with(request -> {
                                    request.setMethod("PATCH"); //PATCH 요청
                                    return request;
                                })
                )
                //then
                .andExpect(status().isOk());
    }

    @Test
    void 상품수정_성공_이미지만전송() throws Exception {
        //given
        MockMultipartFile image = new MockMultipartFile("image", "testImg.png", "image/png", "test image content".getBytes());

        //when
        mockMvc.perform(
                        multipart("/admin/product/" + testProductId)
                                .file(image)
                                .header("LB-Role", "ADMIN")
                                .with(request -> {
                                    request.setMethod("PATCH");
                                    return request;
                                })
                )
                //then
                .andExpect(status().isOk());
    }

    @Test
    void 상품수정_성공_데이터만전송() throws Exception {
        //given
        ProductCreateRequestDto requestData = ProductCreateRequestDto.builder()
                .name("testName")
                .price(100L)
                //일부 데이터 수정 원하지 않는 상황 가정(productState,isCustom x)
                .build();
        String jsonData = objectMapper.writeValueAsString(requestData);

        MockMultipartFile data = new MockMultipartFile("data", "data", "application/json", jsonData.getBytes());

        //when
        mockMvc.perform(
                        multipart("/admin/product/" + testProductId)
                                .file(data)
                                .header("LB-Role", "ADMIN")
                                .with(request -> {
                                    request.setMethod("PATCH");
                                    return request;
                                })
                )
                //then
                .andExpect(status().isOk());
    }

    @Test
    void 상품수정_실패_유효성검사탈락() throws Exception {
        //given
        MockMultipartFile image = new MockMultipartFile("image", "testImg.png", "image/png", "test image content".getBytes());

        ProductCreateRequestDto requestData = ProductCreateRequestDto.builder()
                //이름을 빈칸으로
                .name("")
                //가격을 음수로
                .price(-100L)
                .productState(ProductState.ON_SALE)
                .isCustom(true)
                .build();
        String jsonData = objectMapper.writeValueAsString(requestData);

        MockMultipartFile data = new MockMultipartFile("data", "data", "application/json", jsonData.getBytes());
        //when
        MvcResult result = mockMvc.perform(
                        multipart("/admin/product/" + testProductId)
                                .file(image)
                                .file(data)
                                .header("LB-Role", "ADMIN")
                                .with(request -> {
                                    request.setMethod("PATCH");
                                    return request;
                                })
                )
                //then
                .andExpect(status().isBadRequest())
                .andReturn();
        //에러 관련 전체 텍스트
        String errorContents = result.getResponse().getContentAsString();
        //전체 텍스트에서 이름과 가격 유효성에 대한 예상 커스텀 에러 메세지가 포함되는지 검사
        String expectedErrorMessage = "Field [price] must not be Negative, Field [name] must not be blank";
        assertThat(errorContents, containsString(expectedErrorMessage));
    }

    @Test
    void 상품삭제_성공() throws Exception {
        //given
        //when
        mockMvc.perform(delete("/admin/product/" + testProductId)
                        .header("LB-Role", "ADMIN"))
                //then
                .andExpect(status().isNoContent());
    }
}
