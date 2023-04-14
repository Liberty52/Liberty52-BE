package com.liberty52.product.service.controller;

import static com.liberty52.product.service.utils.MockConstants.*;

import static com.liberty52.product.service.utils.MockFactory.createMockOrderRetrieveResponse;
import static com.liberty52.product.service.utils.MockFactory.createMockOrderRetrieveResponseList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.liberty52.product.service.applicationservice.OrderRetrieveService;
import com.liberty52.product.service.controller.dto.OrdersRetrieveResponse;
import com.netflix.discovery.converters.Auto;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = OrderRetrieveController.class)
class OrderRetrieveControllerTest {

    @InjectMocks
    OrderRetrieveController orderRetrieveController;

    @MockBean
    OrderRetrieveService orderRetrieveService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void retrieveOrderForList () throws Exception{
        //given
        given(orderRetrieveService.retrieveOrders(MOCK_AUTH_ID))
                .willReturn(createMockOrderRetrieveResponseList());

        //when
        mockMvc.perform(get("/product/orders")
                .header(HttpHeaders.AUTHORIZATION,MOCK_AUTH_ID ))
        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(MOCK_LIST_SIZE))
                .andExpect(jsonPath("$.[0].orderId").value(MOCK_ORDER_ID))
                .andExpect(jsonPath("$.[0].orderDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.[0].orderStatus").value(MOCK_ORDER_STATUS_ORDERED.name()))
                .andExpect(jsonPath("$.[0].address").value(MOCK_ADDRESS))
                .andExpect(jsonPath("$.[0].receiverEmail").value(MOCK_RECEIVER_EMAIL))
                .andExpect(jsonPath("$.[0].receiverPhoneNumber").value(MOCK_RECEIVER_PHONE_NUMBER))
                .andExpect(jsonPath("$.[0].receiverName").value(MOCK_RECEIVER_NAME))
                .andExpect(jsonPath("$.[0].productRepresentUrl").value(MOCK_PRODUCT_REPRESENT_URL))
                .andExpect(jsonPath("$.[0].products[0].name").value(MOCK_PRODUCT_NAME))
                .andExpect(jsonPath("$.[0].products[0].quantity").value(MOCK_QUANTITY))
                .andExpect(jsonPath("$.[0].products[0].price").value(MOCK_PRICE))
                .andDo(print());





    }


}