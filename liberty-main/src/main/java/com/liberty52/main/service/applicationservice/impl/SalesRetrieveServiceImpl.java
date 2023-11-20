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

import java.util.List;
import java.util.Objects;

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
                .salesMoney(0L).salesQuantity(0L).build();

        Long totalSalesMoney = result.stream()
                .map(tuple -> tuple.get(0, Long.class))
                .filter(Objects::nonNull)
                .mapToLong(Long::longValue)
                .sum();

        Long totalSalesQuantity = result.stream()
                .map(tuple -> tuple.get(1, Long.class))
                .filter(Objects::nonNull)
                .mapToLong(Long::longValue)
                .sum();

        return SalesResponseDto.builder()
                .salesMoney(totalSalesMoney).salesQuantity(totalSalesQuantity).build();

    }
}
