package com.liberty52.product.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liberty52.product.service.applicationservice.*;
import com.liberty52.product.service.controller.dto.AdminProductDeliveryOptionsDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductInfoController.class)
class ProductInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductInfoRetrieveService productInfoRetrieveService;

    @MockBean
    private ProductIntroductionUpsertService productIntroductionUpsertService;

    @MockBean
    private ProductIntroductionDeleteService productIntroductionDeleteService;

    @MockBean
    private ProductIntroductionImgSaveService productIntroductionImgSaveService;

    @MockBean
    private ProductDeliveryOptionService productDeliveryOptionService;

    @Nested
    @DisplayName("관리자 상품 배송옵션 API")
    class API_ProductDeliveryOption {
        @Nested
        @DisplayName("배송옵션 추가")
        class Creation {
            @Test
            @DisplayName("관리자가 상품의 배송옵션을 추가한다")
            void createProductDeliveryOptions() throws Exception {
                // given
                var productId = "product-id";
                var courierName = "courier_name";
                var fee = 100_000;
                var request = AdminProductDeliveryOptionsDto.Request.builder()
                        .courierName(courierName)
                        .fee(fee)
                        .build();

                given(productDeliveryOptionService.create(any(), any(), any()))
                        .willReturn(AdminProductDeliveryOptionsDto.Response.builder()
                                .productId(productId)
                                .courierName(courierName)
                                .fee(fee)
                                .build());
                // when
                // then
                mockMvc.perform(
                        post("/admin/products/{productId}/deliveryOptions", productId)
                                .header(HttpHeaders.AUTHORIZATION, "admin-id")
                                .header("LB-Role", "ADMIN")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                ).andExpectAll(
                        status().isOk(),
                        jsonPath("$.productId").value(productId),
                        jsonPath("$.courierName").value(courierName),
                        jsonPath("$.fee").value(fee)
                );
            }

            @Test
            @DisplayName("일반 유저는 상품의 배송옵션을 추가할 수 없다")
            void createProductDeliveryOptions_notAdmin() throws Exception {
                // given
                var productId = "product-id";
                var courierName = "courier_name";
                var fee = 100_000;
                var request = AdminProductDeliveryOptionsDto.Request.builder()
                        .courierName(courierName)
                        .fee(fee)
                        .build();
                // when
                // then
                mockMvc.perform(
                        post("/admin/products/{productId}/deliveryOptions", productId)
                                .header(HttpHeaders.AUTHORIZATION, "user-id")
                                .header("LB-Role", "USER")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isForbidden());
            }
        }
    }
}
