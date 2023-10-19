package com.liberty52.product.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liberty52.product.service.applicationservice.OrderCancelService;
import com.liberty52.product.service.applicationservice.OrderCreateService;
import com.liberty52.product.service.applicationservice.OrderRetrieveService;
import com.liberty52.product.service.controller.dto.OrderCancelDto;
import com.liberty52.product.service.controller.dto.OrderDetailRetrieveResponse;
import com.liberty52.product.service.controller.dto.OrdersRetrieveResponse;
import com.liberty52.product.service.entity.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @MockBean private OrderCreateService orderCreateService;
    @MockBean private OrderRetrieveService orderRetrieveService;
    @MockBean private OrderCancelService orderCancelService;

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

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

    @Test
    @DisplayName("주문 상세 조회 API 검증")
    void retrieveOrderDetailTest() throws Exception {
        // given
        OrderDetailRetrieveResponse orderDetailRetrieveResponse = OrderDetailRetrieveResponse.builder()
                .orderId("testOrderId")
                .orderDate("2023-10-19")
                .orderStatus(OrderStatus.READY.getKoName())
                .address("123 Main St, City")
                .receiverEmail("john@example.com")
                .receiverName("John Doe")
                .receiverPhoneNumber("123-456-7890")
                .productRepresentUrl("http://example.com")
                .totalProductPrice(10000L)
                .deliveryFee(3000)
                .totalPrice(13000L)
                .orderNum("123456789")
                .paymentType("Card")
                .paymentInfo(null)
                .products(Collections.emptyList())
                .customerName("John Doe")
                .build();

        given(orderRetrieveService.retrieveOrderDetail(anyString(), anyString()))
                .willReturn(orderDetailRetrieveResponse);

        // when && then
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/testOrderId")
                .header(HttpHeaders.AUTHORIZATION, "Bearer testAuthId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("testOrderId"))
                .andExpect(jsonPath("$.orderDate").value("2023-10-19"))
                .andExpect(jsonPath("$.orderStatus").value("주문접수"))
                .andExpect(jsonPath("$.address").value("123 Main St, City"))
                .andExpect(jsonPath("$.receiverEmail").value("john@example.com"))
                .andExpect(jsonPath("$.receiverName").value("John Doe"))
                .andExpect(jsonPath("$.receiverPhoneNumber").value("123-456-7890"))
                .andExpect(jsonPath("$.productRepresentUrl").value("http://example.com"))
                .andExpect(jsonPath("$.totalProductPrice").value(10000L))
                .andExpect(jsonPath("$.deliveryFee").value(3000))
                .andExpect(jsonPath("$.totalPrice").value(13000L))
                .andExpect(jsonPath("$.orderNum").value("123456789"))
                .andExpect(jsonPath("$.paymentType").value("Card"))
                .andExpect(jsonPath("$.paymentInfo").doesNotExist())
                .andExpect(jsonPath("$.products").isArray())
                .andExpect(jsonPath("$.customerName").value("John Doe"));
    }

    @Test
    @DisplayName("주문 취소 API 검증")
    void cancelOrderTest() throws Exception {
        // given
        OrderCancelDto.Request request = OrderCancelDto.Request.builder()
                .orderId("testOrderId")
                .reason("Test reason")
                .refundBank("Test Bank")
                .refundHolder("Test Holder")
                .refundAccount("Test Account")
                .refundPhoneNum("Test PhoneNum")
                .build();

        OrderCancelDto.Response expectedResponse = OrderCancelDto.Response.of("Order canceled successfully");

        given(orderCancelService.cancelOrder(eq("testAuthId"), any(OrderCancelDto.Request.class)))
                .willReturn(expectedResponse);

        // when && then
        mockMvc.perform(MockMvcRequestBuilders.post("/orders/cancel")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer testAuthId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }



}
