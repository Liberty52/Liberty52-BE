package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.OrderCancelService;
import com.liberty52.product.service.applicationservice.OrderCreateService;
import com.liberty52.product.service.applicationservice.OrderRetrieveService;
import com.liberty52.product.service.controller.dto.OrdersRetrieveResponse;
import com.liberty52.product.service.entity.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @MockBean private OrderCreateService orderCreateService;
    @MockBean private OrderRetrieveService orderRetrieveService;
    @MockBean private OrderCancelService orderCancelService;

    @Autowired private MockMvc mockMvc;

    @Test
    @DisplayName("주문 목록 조회 API 검증")
    void retrieveOrdersTest() throws Exception {
        // given
        OrdersRetrieveResponse ordersRetrieveResponse = OrdersRetrieveResponse.builder()
                .orderId("testOrderId")
                .orderDate("2023-10-19")
                .orderStatus(OrderStatus.READY.getKoName())
                .address("123 Main St, City")
                .receiverName("John Doe")
                .receiverEmail("john@example.com")
                .receiverPhoneNumber("123-456-7890")
                .productRepresentUrl("http://example.com")
                .orderNum("123456789")
                .paymentType("Card")
                .paymentInfo(null)
                .products(Collections.emptyList())
                .build();

        given(orderRetrieveService.retrieveOrders(anyString()))
                .willReturn(Collections.singletonList(ordersRetrieveResponse));

        // when && then
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer testAuthId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].orderId").value("testOrderId"))
                .andExpect(jsonPath("$[0].orderDate").value("2023-10-19"))
                .andExpect(jsonPath("$[0].orderStatus").value("주문접수"))
                .andExpect(jsonPath("$[0].address").value("123 Main St, City"))
                .andExpect(jsonPath("$[0].receiverName").value("John Doe"))
                .andExpect(jsonPath("$[0].receiverEmail").value("john@example.com"))
                .andExpect(jsonPath("$[0].receiverPhoneNumber").value("123-456-7890"))
                .andExpect(jsonPath("$[0].productRepresentUrl").value("http://example.com"))
                .andExpect(jsonPath("$[0].orderNum").value("123456789"))
                .andExpect(jsonPath("$[0].paymentType").value("Card"));
    }


}
