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
                .totalSalesMoney(0L).totalSalesQuantity(0L).monthlySales(Collections.emptyMap()).build();

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

        Map<String, Map<String, Object>> monthlySales = result.stream()
                .collect(Collectors.toMap(
                        tuple -> {
                            Integer year = tuple.get(orders.orderedAt.year());
                            Integer month = tuple.get(orders.orderedAt.month());
                            if (year != null && month != null) {
                                return year.toString() + " - " + month.toString();
                            }
                            return "no OrderedDate";
                        },
                        tuple -> {
                            Map<String, Object> resultMap = new HashMap<>();
                            resultMap.put("salesMoney", tuple.get(orders.amount.sum()));
                            resultMap.put("salesQuantity", tuple.get(customProduct.quantity.sum()));
                            return resultMap;
                        },
                        (existing, replacement) -> existing
                ));

        return SalesResponseDto.builder()
                .totalSalesMoney(totalSalesMoney).totalSalesQuantity(totalSalesQuantity).monthlySales(monthlySales).build();

    }
}
