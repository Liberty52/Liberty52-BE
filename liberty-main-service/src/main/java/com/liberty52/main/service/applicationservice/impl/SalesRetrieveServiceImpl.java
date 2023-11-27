package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.main.global.util.Validator;
import com.liberty52.main.service.applicationservice.SalesRetrieveService;
import com.liberty52.main.service.controller.dto.SalesRequestDto;
import com.liberty52.main.service.controller.dto.SalesResponseDto;
import com.liberty52.main.service.repository.OrderQueryDslRepository;
import com.liberty52.main.service.repository.OrdersRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.liberty52.main.service.entity.QCustomProduct.customProduct;
import static com.liberty52.main.service.entity.QOrders.orders;

@Service
@RequiredArgsConstructor
public class SalesRetrieveServiceImpl implements SalesRetrieveService {
    final OrdersRepository ordersRepository;
    private final OrderQueryDslRepository orderQueryDslRepository;

    @Override
    public SalesResponseDto retrieveSales(String role, SalesRequestDto salesRequestDto) {
        Validator.isAdmin(role);
        List<Tuple> result = orderQueryDslRepository.retrieveByConditions(salesRequestDto);
        if (result.isEmpty()) return SalesResponseDto.builder()
                .totalSalesMoney(0L).totalSalesQuantity(0L).monthlySales(Collections.emptyList()).build();

        Long totalSalesMoney = result.stream()
                .map(tuple -> tuple.get(0, Long.class))
                .filter(Objects::nonNull)
                .mapToLong(Long::longValue)
                .sum();

        Long totalSalesQuantity = result.stream()
                .map(tuple -> tuple.get(1, Integer.class))
                .filter(Objects::nonNull)
                .mapToLong(Integer::longValue)
                .sum();

        return SalesResponseDto.builder()
                .totalSalesMoney(totalSalesMoney)
                .totalSalesQuantity(totalSalesQuantity)
                .monthlySales(result.stream()
                        .map(tuple -> {
                            Integer year = tuple.get(orders.orderedAt.year());
                            Integer month = tuple.get(orders.orderedAt.month());
                            SalesResponseDto.MonthlySales monthlySales = new SalesResponseDto.MonthlySales();
                            if (year != null && month != null) {
                                monthlySales.setYear(year.toString());
                                monthlySales.setMonth(month.toString());
                            } else {
                                monthlySales.setYear("no OrderedDate");
                                monthlySales.setMonth("no OrderedDate");
                            }
                            monthlySales.setSalesMoney(tuple.get(orders.amount.sum()));
                            monthlySales.setSalesQuantity(tuple.get(customProduct.quantity.sum()));
                            return monthlySales;
                        })
                        .collect(Collectors.toList()))
                .build();
    }
}
