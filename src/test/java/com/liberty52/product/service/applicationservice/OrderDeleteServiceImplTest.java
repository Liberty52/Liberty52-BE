package com.liberty52.product.service.applicationservice;

import com.liberty52.product.MockS3Test;
import com.liberty52.product.service.controller.dto.OrderCreateRequestDto;
import com.liberty52.product.service.controller.dto.PaymentCardResponseDto;
import com.liberty52.product.service.entity.OrderDestination;
import com.liberty52.product.service.entity.OrderStatus;
import com.liberty52.product.service.entity.Orders;
import com.liberty52.product.service.entity.payment.PaymentStatus;
import com.liberty52.product.service.entity.payment.PaymentType;
import com.liberty52.product.service.repository.CustomProductRepository;
import com.liberty52.product.service.repository.OrdersRepository;
import com.liberty52.product.service.repository.ProductOptionRepository;
import com.liberty52.product.service.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderDeleteServiceImplTest {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderDeleteService orderDeleteService;

    @Test
    void test_deleteOrderOfReadyByScheduled() throws Exception {
        Long N = 10L;
        for (int i = 0; i < N; i++) {
            preregisterCardPaymentOrders();
        }
        Long beforeCount = ordersRepository.countAllByOrderStatusAndOrderDateLessThan(OrderStatus.READY, LocalDate.now());
        Assertions.assertEquals(N, beforeCount);
        orderDeleteService.deleteOrderOfReadyByScheduled();
        Long afterCount = ordersRepository.countAllByOrderStatusAndOrderDateLessThan(OrderStatus.READY, LocalDate.now());
        Assertions.assertEquals(0, afterCount);
    }

    void preregisterCardPaymentOrders() throws Exception {
        Orders order = Orders.create(
                UUID.randomUUID().toString(),
                OrderDestination.create("receiverName", "receiverEmail", "receiverPhoneNumber", "address1", "address2", "zipCode")
        );
        Field orderDate = order.getClass().getDeclaredField("orderDate");
        orderDate.setAccessible(true);
        orderDate.set(order, LocalDate.now().minusDays(1));
        ordersRepository.save(order);
    }

}