package com.liberty52.main.service.controller.guest;

import com.liberty52.main.global.exception.external.ErrorResponse;
import com.liberty52.main.global.exception.external.RestExceptionHandler;
import com.liberty52.main.global.exception.external.badrequest.CannotAccessOrderException;
import com.liberty52.main.service.applicationservice.OrderCreateService;
import com.liberty52.main.service.applicationservice.OrderDeliveryService;
import com.liberty52.main.service.applicationservice.OrderRetrieveService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static com.liberty52.main.service.utils.MockConstants.*;
import static com.liberty52.main.service.utils.MockFactory.createMockOrderDetailRetrieveResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {OrderGuestController.class, RestExceptionHandler.class})
public class OrderGuestControllerTest {

    final String ORDER_URL = "/orders";
    final String GUEST_PREFIX = "/guest";
    @MockBean
    RestExceptionHandler exceptionHandler;
    @MockBean
    private OrderCreateService orderCreateService;
    @MockBean
    private OrderRetrieveService orderRetrieveService;
    @MockBean
    private OrderDeliveryService orderDeliveryService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void retrieveGuestOrderDetail() throws Exception {
        //given
        given(orderRetrieveService.retrieveGuestOrderDetail(MOCK_AUTH_ID, MOCK_ORDER_ID))
                .willReturn(createMockOrderDetailRetrieveResponse());

        //when
        mockMvc.perform(get(GUEST_PREFIX + ORDER_URL + "/" + MOCK_ORDER_ID)
                        .header(HttpHeaders.AUTHORIZATION, MOCK_AUTH_ID))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(MOCK_ORDER_ID))
                .andExpect(jsonPath("$.orderDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.orderStatus").value(MOCK_ORDER_STATUS_ORDERED.name()))
                .andExpect(jsonPath("$.address").value(MOCK_ADDRESS))
                .andExpect(jsonPath("$.receiverEmail").value(MOCK_RECEIVER_EMAIL))
                .andExpect(jsonPath("$.receiverPhoneNumber").value(MOCK_RECEIVER_PHONE_NUMBER))
                .andExpect(jsonPath("$.receiverName").value(MOCK_RECEIVER_NAME))
                .andExpect(jsonPath("$.totalProductPrice").value(MOCK_TOTAL_PRODUCT_PRICE))
                .andExpect(jsonPath("$.deliveryFee").value(MOCK_DELIVERY_FEE))
                .andExpect(jsonPath("$.totalPrice").value(MOCK_TOTAL_PRICE))
                .andExpect(jsonPath("$.products[0].name").value(MOCK_PRODUCT_NAME))
                .andExpect(jsonPath("$.products[0].quantity").value(MOCK_QUANTITY))
                .andExpect(jsonPath("$.products[0].price").value(MOCK_PRICE))
                .andExpect(jsonPath("$.products[0].productUrl").value(MOCK_PRODUCT_REPRESENT_URL))
                .andDo(print());
    }

    @Test
    void retrieveGuestOrderDetail_throw_cannot_access() throws Exception {
        //given
        given(orderRetrieveService.retrieveGuestOrderDetail(MOCK_AUTH_ID, MOCK_ORDER_ID))
                .willThrow(CannotAccessOrderException.class);
        given(exceptionHandler.handleGlobalException(any(), any()))
                .willReturn(
                        ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(ErrorResponse.createErrorResponse(new CannotAccessOrderException(), ORDER_URL + "/" + MOCK_ORDER_ID))
                );

        //when         //then
        mockMvc.perform(get(GUEST_PREFIX + ORDER_URL + "/" + MOCK_ORDER_ID)
                        .header(HttpHeaders.AUTHORIZATION, MOCK_AUTH_ID))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void 비회원_유저가_자신의_주문에_대한_실시간배송정보를_조회한다() throws Exception {
        // given
        given(orderDeliveryService.getGuestRealTimeDeliveryInfoRedirectUrl(any(), anyString(), anyString(), anyString()))
                .willReturn("This is HTML response from Smart Courier API");
        // when
        // then
        mockMvc.perform(get("/guest/orders/{orderNum}/delivery", "orderNumber")
                .param("courierCode", "04")
                .param("trackingNumber", "1234567890")
                .contentType(MediaType.TEXT_HTML)
                .header(HttpHeaders.AUTHORIZATION, "GUEST-1")
        ).andExpectAll(
                status().is3xxRedirection()
        );
    }
}
