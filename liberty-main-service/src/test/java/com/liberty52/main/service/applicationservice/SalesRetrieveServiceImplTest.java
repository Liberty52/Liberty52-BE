package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.SalesRequestDto;
import com.liberty52.main.service.controller.dto.SalesResponseDto;
import com.liberty52.main.service.entity.CustomProduct;
import com.liberty52.main.service.entity.OrderStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static com.liberty52.main.global.constants.RoleConstants.ADMIN;

@SpringBootTest
public class SalesRetrieveServiceImplTest {
    @Autowired
    private SalesRetrieveService salesRetrieveService;
    @Autowired
    private EntityManager entityManager;

    @Test
    void 매출조회_시작일() {
        // Given
        SalesRequestDto requestDto = SalesRequestDto.builder().startDate(LocalDate.parse("2099-12-31")).build();
        // When
        SalesResponseDto responseDto = salesRetrieveService.retrieveSales(ADMIN, requestDto);
        // Then
        Assertions.assertEquals(0, responseDto.getSalesQuantity());
    }

    @Test
    void 매출조회_조건없음() {
        // Given
        SalesRequestDto requestDto = SalesRequestDto.builder().build();
        // When
        SalesResponseDto responseDto = salesRetrieveService.retrieveSales(ADMIN, requestDto);
        TypedQuery<CustomProduct> query = entityManager.createQuery(
                "SELECT c FROM CustomProduct c " +
                        "JOIN FETCH c.orders " +
                        "WHERE c.orders.orderStatus NOT IN (:param1, :param2, :param3)",
                CustomProduct.class);

        query
                .setParameter("param1", OrderStatus.CANCEL_REQUESTED)
                .setParameter("param2", OrderStatus.CANCELED)
                .setParameter("param3", OrderStatus.REFUND);
        List<CustomProduct> found = query.getResultList();
        //Then
        Assertions.assertEquals(found.size(), responseDto.getSalesQuantity());
    }

    @Test
    void 매출조회_제품명() {
        // Given
        String testProductName = "Liberty 52_Frame";
        SalesRequestDto requestDto = SalesRequestDto.builder().productName(testProductName).build();
        // When
        SalesResponseDto responseDto = salesRetrieveService.retrieveSales(ADMIN, requestDto);
        TypedQuery<CustomProduct> query = entityManager.createQuery(
                "SELECT c FROM CustomProduct c " +
                        "JOIN FETCH c.product p " +
                        "JOIN FETCH c.orders " +
                        "WHERE c.orders.orderStatus NOT IN (:param1, :param2, :param3) " +
                        "AND p.name = :productName",
                CustomProduct.class);

        query
                .setParameter("param1", OrderStatus.CANCEL_REQUESTED)
                .setParameter("param2", OrderStatus.CANCELED)
                .setParameter("param3", OrderStatus.REFUND)
                .setParameter("productName", testProductName);
        List<CustomProduct> found = query.getResultList();

        Long foundSalesMoney = 0L;
        for (CustomProduct customProduct : found) {
            foundSalesMoney += customProduct.getOrders().getAmount();
        }
        // Then
        Assertions.assertEquals(found.size(), responseDto.getSalesQuantity());
        Assertions.assertEquals(foundSalesMoney, responseDto.getSalesMoney());
    }
}
