package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.impl.DeliveryOptionRetrieveServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DeliveryOptionRetrieveController.class)
class DeliveryOptionRetrieveControllerTest {

    @MockBean
    private DeliveryOptionRetrieveServiceImpl deliveryOptionRetrieveService;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("기본 배송비 조회")
    void getDefaultDeliveryFee() throws Exception {
        // given
        given(deliveryOptionRetrieveService.getDefaultDeliveryFee())
                .willReturn(100000);
        // when
        // then
        mvc.perform(get("/options/delivery/fee")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.fee").value("100,000"));
    }
}