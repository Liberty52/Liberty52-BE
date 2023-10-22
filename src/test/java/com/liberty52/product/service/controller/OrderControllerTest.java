package com.liberty52.product.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liberty52.product.global.exception.external.notfound.NotFoundException;
import com.liberty52.product.service.applicationservice.OrderCancelService;
import com.liberty52.product.service.applicationservice.OrderCreateService;
import com.liberty52.product.service.applicationservice.OrderRetrieveService;
import com.liberty52.product.service.controller.dto.*;
import com.liberty52.product.service.entity.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @MockBean private OrderCreateService orderCreateService;
    @MockBean private OrderRetrieveService orderRetrieveService;
    @MockBean private OrderCancelService orderCancelService;

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    @DisplayName("주문 목록 조회 - 성공")
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
    @DisplayName("주문 상세 조회 - 성공")
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
                .orderDelivery(OrderDeliveryDto.builder()
                        .id("od-id")
                        .code("01")
                        .name("택배사")
                        .trackingNumber("1234567890")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build())
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
                .andExpect(jsonPath("$.customerName").value("John Doe"))
                .andExpect(jsonPath("$.orderDelivery.id").value("od-id"))
                .andExpect(jsonPath("$.orderDelivery.code").value("01"))
                .andExpect(jsonPath("$.orderDelivery.name").value("택배사"))
                .andExpect(jsonPath("$.orderDelivery.trackingNumber").value("1234567890"))
                .andExpect(jsonPath("$.orderDelivery.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.orderDelivery.updatedAt").isNotEmpty());
    }

    @Test
    @DisplayName("주문 취소 - 성공")
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

    @Test
    @DisplayName("카드 결제 주문 생성 - 성공")
    void createCardPaymentOrderSuccess() throws Exception {
        // given
        OrderCreateRequestDto dto = OrderCreateRequestDto.forTestCard(
                "testOrderId",
                List.of("testOptionId1", "testOptionId2"),
                1,
                List.of("testOrderOption1"),
                "testReceiverName",
                "testReceiverEmail",
                "testReceiverPhoneNumber",
                "testAddress",
                "testProductRepresentUrl",
                "testZipCode"
        );
        MultipartFile imageFile = new MockMultipartFile("imageFile", "test.jpg", "image/jpeg", "test image content".getBytes());
        PaymentCardResponseDto expectedResponse = PaymentCardResponseDto.of("testNerchandId", "testOrderId", 1L);

        given(orderCreateService.createCardPaymentOrders(anyString(), any(OrderCreateRequestDto.class), any(MultipartFile.class)))
                .willReturn(expectedResponse);

        // when && then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/orders/card") // 멀티파트 요청을 만듭니다.
                        .file(new MockMultipartFile("dto", "dto.json", "application/json", objectMapper.writeValueAsBytes(dto))) // dto 파트를 추가합니다.
                        .file(new MockMultipartFile("imageFile", "test.jpg", "image/jpeg", imageFile.getBytes())) // imageFile 파트를 추가합니다.
                        .header(HttpHeaders.AUTHORIZATION, "Bearer testAuthId")
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.merchantId").value("testNerchandId"))
                .andExpect(jsonPath("$.orderNum").value("testOrderId"))
                .andExpect(jsonPath("$.amount").value(1L));

    }

    @Test
    @DisplayName("카드 결제 주문 생성 - 실패 - 요청이 잘못된 경우")
    void createCardPaymentOrderFailureOnMissingParameters() throws Exception {
        // when && then
        mockMvc.perform(multipart("/orders/card")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer testAuthId")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("카드 결제 주문 최종 승인 확인 - 성공")
    void testConfirmFinalApprovalOfCardPaymentWhenOrderExistsThenReturnSuccess() throws Exception {
        // given
        PaymentConfirmResponseDto expectedResponse = PaymentConfirmResponseDto.of("testOrderId", "testOrderNum");

        given(orderCreateService.confirmFinalApprovalOfCardPayment(anyString(), anyString()))
                .willReturn(expectedResponse);

        // when && then
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/card/testOrderId/confirm")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer testAuthId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("testOrderId"))
                .andExpect(jsonPath("$.orderNum").value("testOrderNum"));
    }

    @Test
    @DisplayName("카드 결제 주문 최종 승인 확인 - 실패 - 주문이 존재하지 않는 경우")
    void testConfirmFinalApprovalOfCardPaymentWhenOrderDoesNotExistThenReturnNotFound() throws Exception {
        // given
        given(orderCreateService.confirmFinalApprovalOfCardPayment(anyString(), anyString()))
                .willThrow(new NotFoundException("Order not found"));

        // when && then
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/card/nonExistentOrderId/confirm")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer testAuthId"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Order not found"));
    }

    @Test
    @DisplayName("가상계좌 결제 주문 생성 - 성공")
    void createVBankPaymentOrderSuccess() throws Exception {
        // given
        OrderCreateRequestDto dto = OrderCreateRequestDto.forTestVBank(
                "testOrderId",
                List.of("testOptionId1", "testOptionId2"),
                1,
                List.of("testOrderOption1"),
                "testReceiverName",
                "testReceiverEmail",
                "testReceiverPhoneNumber",
                "testAddress1",
                "testAddress2",
                "testZipCode",
                "testVBankInfo",
                "testDepositorName"
        );
        MultipartFile imageFile = new MockMultipartFile("imageFile", "test.jpg", "image/jpeg", "test image content".getBytes());
        PaymentVBankResponseDto expectedResponse = PaymentVBankResponseDto.of("orderId", "orderNum");

        given(orderCreateService.createVBankPaymentOrders(anyString(), any(OrderCreateRequestDto.class), any(MultipartFile.class)))
                .willReturn(expectedResponse);

        // when && then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/orders/vbank")
                        .file(new MockMultipartFile("dto", "dto.json", "application/json", objectMapper.writeValueAsBytes(dto)))
                        .file(new MockMultipartFile("imageFile", "test.jpg", "image/jpeg", imageFile.getBytes()))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer testAuthId")
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderId").value("orderId"))
                .andExpect(jsonPath("$.orderNum").value("orderNum"));

    }

    @Test
    @DisplayName("가상계좌 결제 주문 생성 - 실패 - 요청이 잘못된 경우")
    void createVBankPaymentOrderFailureOnMissingParameters() throws Exception {
        // when && then
        mockMvc.perform(multipart("/orders/vbank")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer testAuthId")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());
    }




    @Test
    @DisplayName("카트를 이용한 카드 결제 주문 생성 - 성공")
    void createCardPaymentOrdersByCarts() throws Exception {
        // given
        OrderCreateRequestDto dto = OrderCreateRequestDto.forTestCard(
                "testOrderId",
                List.of("testOptionId1", "testOptionId2"),
                1,
                List.of("testOrderOption1"),
                "testReceiverName",
                "testReceiverEmail",
                "testReceiverPhoneNumber",
                "testAddress",
                "testProductRepresentUrl",
                "testZipCode"
        );
        PaymentCardResponseDto expectedResponse = PaymentCardResponseDto.of("testNerchandId", "testOrderId", 1L);

        given(orderCreateService.createCardPaymentOrdersByCarts(anyString(), any(OrderCreateRequestDto.class)))
                .willReturn(expectedResponse);

        // when && then
        mockMvc.perform(MockMvcRequestBuilders.post("/orders/card/carts")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer testAuthId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.merchantId").value("testNerchandId"))
                .andExpect(jsonPath("$.orderNum").value("testOrderId"))
                .andExpect(jsonPath("$.amount").value(1L));
    }

    @Test
    @DisplayName("카트를 이용한 가상 계좌 결제 주문 생성 - 성공")
    void createVBankPaymentOrdersByCarts() throws Exception {
        // given
        OrderCreateRequestDto dto = OrderCreateRequestDto.forTestVBank(
                "testOrderId",
                List.of("testOptionId1", "testOptionId2"),
                1,
                List.of("testOrderOption1"),
                "testReceiverName",
                "testReceiverEmail",
                "testReceiverPhoneNumber",
                "testAddress1",
                "testAddress2",
                "testZipCode",
                "testVBankInfo",
                "testDepositorName"
        );
        PaymentVBankResponseDto expectedResponse = PaymentVBankResponseDto.of("orderId", "orderNum");

        given(orderCreateService.createVBankPaymentOrdersByCarts(anyString(), any(OrderCreateRequestDto.class)))
                .willReturn(expectedResponse);

        // when && then
        mockMvc.perform(MockMvcRequestBuilders.post("/orders/vbank/carts")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer testAuthId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderId").value("orderId"))
                .andExpect(jsonPath("$.orderNum").value("orderNum"));
    }




}
