package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.main.global.adapter.cloud.AuthServiceClient;
import com.liberty52.main.service.controller.dto.AuthClientDataResponse;
import com.liberty52.main.service.entity.CanceledOrders;
import com.liberty52.main.service.entity.OrderStatus;
import com.liberty52.main.service.entity.Orders;
import com.liberty52.main.service.repository.OrderQueryDslRepository;
import com.liberty52.main.service.repository.OrderQueryDslRepositoryImpl;
import com.liberty52.main.service.utils.MockFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderRetrieveServiceImplTest {

    @InjectMocks
    private OrderRetrieveServiceImpl service;

    @Mock
    private OrderQueryDslRepository orderQueryDslRepository;

    @Mock
    private AuthServiceClient authServiceClient;

    @Test
    @DisplayName("유저가 주문 리스트를 조회한다")
    void retrieveOrders() {
        var orders = new ArrayList<Orders>();
        for (int i = 0; i < 100; i++) {
            orders.add(MockFactory.createOrder("1"));
        }
        given(orderQueryDslRepository.retrieveOrders(anyString()))
                .willReturn(orders);

        var result = service.retrieveOrders("1");

        assertFalse(result.isEmpty());
        assertEquals(100, result.size());
        orders.forEach(it -> assertEquals("1", it.getAuthId()));
    }

    @Test
    @DisplayName("유저가 주문 상세정보를 조회한다")
    void retrieveOrderDetail() {
        var order = MockFactory.createOrder("1");
        given(orderQueryDslRepository.retrieveOrderDetail("1", order.getId()))
                .willReturn(Optional.of(order));

        var result = service.retrieveOrderDetail("1", order.getId());

        assertNotNull(result);
        assertEquals(order.getId(), result.getOrderId());
        assertEquals(order.getOrderNum(), result.getOrderNum());
    }

    @Test
    @DisplayName("익명유저가 주문 상세정보를 조회한다")
    void retrieveGuestOrderDetail() {
        var order = MockFactory.createOrder("G-1");
        given(orderQueryDslRepository.retrieveGuestOrderDetail(anyString(), anyString()))
                .willReturn(Optional.of(order));

        var result = service.retrieveGuestOrderDetail("G-1", "ordernum");

        assertNotNull(result);
        assertEquals(order.getId(), result.getOrderId());
        assertEquals(order.getOrderNum(), result.getOrderNum());
    }

    @Test
    @DisplayName("관리자가 주문 리스트를 조회한다")
    void retrieveOrdersByAdmin() {
        var orders = new ArrayList<Orders>();
        var random = new Random();
        for (int i = 0; i < 100; i++) {
            var authId = random.nextInt(10) + 1;
            orders.add(MockFactory.createOrder(String.valueOf(authId)));
        }
        given(orderQueryDslRepository.retrieveOrdersByAdmin(any(Pageable.class)))
                .willReturn(orders);

        var pageInfo = OrderQueryDslRepositoryImpl.PageInfo.builder()
                .startPage(1L)
                .currentPage(1L)
                .lastPage(100L)
                .totalLastPage(100L)
                .build();
        given(orderQueryDslRepository.getPageInfo(any(Pageable.class)))
                .willReturn(pageInfo);

        var customerInfos = new HashMap<String, AuthClientDataResponse>();
        orders.forEach(it ->
                customerInfos.put(it.getAuthId(), new AuthClientDataResponse("name_" + it.getAuthId(), null)));
        given(authServiceClient.retrieveAuthData(anySet()))
                .willReturn(customerInfos);

        var result = service.retrieveOrdersByAdmin("ADMIN", PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(100, result.getOrders().size());
        assertEquals(1L, result.getStartPage());
        assertEquals(1L, result.getCurrentPage());
        assertEquals(100L, result.getLastPage());
        assertEquals(100L, result.getTotalLastPage());
        for (int i = 0; i < result.getOrders().size(); i++) {
            var expect = orders.get(i);
            var actual = result.getOrders().get(i);
            assertEquals(expect.getId(), actual.getOrderId());
            assertEquals(expect.getAuthId(), actual.getCustomerId());
            assertEquals("name_" + expect.getAuthId(), actual.getCustomerName());
        }
    }

    @Test
    @DisplayName("관리자가 주문 리스트를 조회할 경우 리스트가 비어있다면 비어있는 응답값을 조회한다")
    void retrieveOrdersByAdmin_noResult() {
        given(orderQueryDslRepository.retrieveOrdersByAdmin(any(Pageable.class)))
                .willReturn(new ArrayList<>());

        var result = service.retrieveOrdersByAdmin("ADMIN", PageRequest.of(0, 10));

        assertNotNull(result);
        assertTrue(result.getOrders().isEmpty());
        assertEquals(0L, result.getStartPage());
        assertEquals(0L, result.getCurrentPage());
        assertEquals(0L, result.getLastPage());
        assertEquals(0L, result.getTotalLastPage());
    }

    @Test
    @DisplayName("관리자가 주문 상세정보를 조회한다")
    void retrieveOrderDetailByAdmin() {
        var order = MockFactory.createOrder("1");
        given(orderQueryDslRepository.retrieveOrderDetailByOrderId(anyString()))
                .willReturn(Optional.of(order));

        var customerInfos = new HashMap<String, AuthClientDataResponse>();
        customerInfos.put("1", new AuthClientDataResponse("name_1", null));
        given(authServiceClient.retrieveAuthData(anySet()))
                .willReturn(customerInfos);

        var result = service.retrieveOrderDetailByAdmin("ADMIN", order.getId());

        assertNotNull(result);
        assertEquals(order.getId(), result.getOrderId());
        assertEquals("name_1", result.getCustomerName());
    }

    @Test
    @DisplayName("관리자가 취소 주문 또는 취소 요청 주문 리스트를 조회한다")
    void retrieveCanceledOrdersByAdmin() {
        var orders = new ArrayList<Orders>();
        var random = new Random();
        for (int i = 0; i < 100; i++) {
            var authId = random.nextInt(10) + 1;
            var order = MockFactory.createOrder(String.valueOf(authId));
            if (i % 2 == 0) {
                order.changeOrderStatusToCanceled();
            } else {
                order.changeOrderStatusToCancelRequest();
            }
            order.changeOrderStatusToCanceled();
            CanceledOrders.of("", order);
            orders.add(order);
        }
        given(orderQueryDslRepository.retrieveCanceledOrdersByAdmin(any(Pageable.class)))
                .willReturn(orders);

        var pageInfo = OrderQueryDslRepositoryImpl.PageInfo.builder()
                .startPage(1L)
                .currentPage(1L)
                .lastPage(100L)
                .totalLastPage(100L)
                .build();
        given(orderQueryDslRepository.getCanceledOrdersPageInfo(any(Pageable.class), any()))
                .willReturn(pageInfo);

        var customerInfos = new HashMap<String, AuthClientDataResponse>();
        orders.forEach(it ->
                customerInfos.put(it.getAuthId(), new AuthClientDataResponse("name_" + it.getAuthId(), null)));
        given(authServiceClient.retrieveAuthData(anySet()))
                .willReturn(customerInfos);

        var result = service.retrieveCanceledOrdersByAdmin("ADMIN", PageRequest.of(1, 100));

        assertNotNull(result);
        assertFalse(result.getOrders().isEmpty());
        assertEquals(100, result.getOrders().size());
        assertEquals(1L, result.getStartPage());
        assertEquals(1L, result.getCurrentPage());
        assertEquals(100L, result.getLastPage());
        assertEquals(100L, result.getTotalLastPage());
        for (int i = 0; i < result.getOrders().size(); i++) {
            var expect = orders.get(i);
            var actual = result.getOrders().get(i);
            assertEquals(expect.getId(), actual.getOrderId());
            assertEquals(expect.getAuthId(), actual.getCustomerId());
            assertEquals("name_" + expect.getAuthId(), actual.getCustomerName());
            assertTrue(
                    OrderStatus.CANCELED.getKoName().equals(actual.getOrderStatus()) ||
                            OrderStatus.CANCEL_REQUESTED.getKoName().equals(actual.getOrderStatus())
            );
        }
    }

    @Test
    @DisplayName("관리자가 취소 주문 또는 취소 요청 주문 리스트를 조회할 경우 리스트가 비어있다면 비어있는 응답값을 반환한다")
    void retrieveCanceledOrdersByAdmin_noResult() {
        given(orderQueryDslRepository.retrieveCanceledOrdersByAdmin(any(Pageable.class)))
                .willReturn(new ArrayList<>());

        var result = service.retrieveCanceledOrdersByAdmin("ADMIN", PageRequest.of(1, 100));

        assertNotNull(result);
        assertTrue(result.getOrders().isEmpty());
        assertEquals(0L, result.getStartPage());
        assertEquals(0L, result.getCurrentPage());
        assertEquals(0L, result.getLastPage());
        assertEquals(0L, result.getTotalLastPage());
    }

    @Test
    @DisplayName("관리자가 취소 요청 주문 리스트를 조회한다")
    void retrieveOnlyRequestedCanceledOrdersByAdmin() {
        var orders = new ArrayList<Orders>();
        var random = new Random();
        for (int i = 0; i < 100; i++) {
            var authId = random.nextInt(10) + 1;
            var order = MockFactory.createOrder(String.valueOf(authId));
            order.changeOrderStatusToCancelRequest();
            CanceledOrders.of("", order);
            orders.add(order);
        }
        given(orderQueryDslRepository.retrieveOnlyRequestedCanceledOrdersByAdmin(any(Pageable.class)))
                .willReturn(orders);

        var pageInfo = OrderQueryDslRepositoryImpl.PageInfo.builder()
                .startPage(1L)
                .currentPage(1L)
                .lastPage(100L)
                .totalLastPage(100L)
                .build();
        given(orderQueryDslRepository.getCanceledOrdersPageInfo(any(Pageable.class), any()))
                .willReturn(pageInfo);

        var customerInfos = new HashMap<String, AuthClientDataResponse>();
        orders.forEach(it ->
                customerInfos.put(it.getAuthId(), new AuthClientDataResponse("name_" + it.getAuthId(), null)));
        given(authServiceClient.retrieveAuthData(anySet()))
                .willReturn(customerInfos);

        var result = service.retrieveOnlyRequestedCanceledOrdersByAdmin("ADMIN", PageRequest.of(1, 100));

        assertNotNull(result);
        assertFalse(result.getOrders().isEmpty());
        assertEquals(100, result.getOrders().size());
        assertEquals(1L, result.getStartPage());
        assertEquals(1L, result.getCurrentPage());
        assertEquals(100L, result.getLastPage());
        assertEquals(100L, result.getTotalLastPage());
        for (int i = 0; i < result.getOrders().size(); i++) {
            var expect = orders.get(i);
            var actual = result.getOrders().get(i);
            assertEquals(expect.getId(), actual.getOrderId());
            assertEquals(expect.getAuthId(), actual.getCustomerId());
            assertEquals("name_" + expect.getAuthId(), actual.getCustomerName());
            assertEquals(OrderStatus.CANCEL_REQUESTED.getKoName(), actual.getOrderStatus());
        }
    }

    @Test
    @DisplayName("관리자가 취소 주문 리스트를 조회한다")
    void retrieveOnlyCanceledOrdersByAdmin() {
        var orders = new ArrayList<Orders>();
        var random = new Random();
        for (int i = 0; i < 100; i++) {
            var authId = random.nextInt(10) + 1;
            var order = MockFactory.createOrder(String.valueOf(authId));
            order.changeOrderStatusToCanceled();
            CanceledOrders.of("", order);
            orders.add(order);
        }
        given(orderQueryDslRepository.retrieveOnlyCanceledOrdersByAdmin(any(Pageable.class)))
                .willReturn(orders);

        var pageInfo = OrderQueryDslRepositoryImpl.PageInfo.builder()
                .startPage(1L)
                .currentPage(1L)
                .lastPage(100L)
                .totalLastPage(100L)
                .build();
        given(orderQueryDslRepository.getCanceledOrdersPageInfo(any(Pageable.class), any()))
                .willReturn(pageInfo);

        var customerInfos = new HashMap<String, AuthClientDataResponse>();
        orders.forEach(it ->
                customerInfos.put(it.getAuthId(), new AuthClientDataResponse("name_" + it.getAuthId(), null)));
        given(authServiceClient.retrieveAuthData(anySet()))
                .willReturn(customerInfos);

        var result = service.retrieveOnlyCanceledOrdersByAdmin("ADMIN", PageRequest.of(1, 100));

        assertNotNull(result);
        assertFalse(result.getOrders().isEmpty());
        assertEquals(100, result.getOrders().size());
        assertEquals(1L, result.getStartPage());
        assertEquals(1L, result.getCurrentPage());
        assertEquals(100L, result.getLastPage());
        assertEquals(100L, result.getTotalLastPage());
        for (int i = 0; i < result.getOrders().size(); i++) {
            var expect = orders.get(i);
            var actual = result.getOrders().get(i);
            assertEquals(expect.getId(), actual.getOrderId());
            assertEquals(expect.getAuthId(), actual.getCustomerId());
            assertEquals("name_" + expect.getAuthId(), actual.getCustomerName());
            assertEquals(OrderStatus.CANCELED.getKoName(), actual.getOrderStatus());
        }
    }

    @Test
    @DisplayName("관리자가 취소 주문 상세정보를 조회한다")
    void retrieveCanceledOrderDetailByAdmin() {
        var order = MockFactory.createOrder("1");
        order.changeOrderStatusToCanceled();
        CanceledOrders.of("reason", order);
        given(orderQueryDslRepository.retrieveOrderDetailWithCanceledOrdersByAdmin(anyString()))
                .willReturn(Optional.of(order));

        var customerInfo = new HashMap<String, AuthClientDataResponse>();
        customerInfo.put("1", new AuthClientDataResponse("name_1", null));
        given(authServiceClient.retrieveAuthData(anySet()))
                .willReturn(customerInfo);

        var result = service.retrieveCanceledOrderDetailByAdmin("ADMIN", order.getId());

        assertNotNull(result);
        assertEquals(order.getId(), result.getBasicOrderDetail().getOrderId());
        assertEquals(OrderStatus.CANCELED.getKoName(), result.getBasicOrderDetail().getOrderStatus());
        assertEquals("reason", result.getCanceledInfo().getReason());
        assertNotNull(result.getCanceledInfo().getCanceledAt());
    }
}
