package com.liberty52.product.service.applicationservice.impl;

import com.liberty52.product.global.exception.external.badrequest.BadRequestException;
import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.product.service.entity.OptionDetail;
import com.liberty52.product.service.repository.OptionDetailRepository;
import com.liberty52.product.service.utils.MockFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OptionDetailStockManageServiceImplTest {

    @InjectMocks private OptionDetailStockManageServiceImpl service;
    @Mock private OptionDetailRepository optionDetailRepository;

    private OptionDetail initOptionDetail(int stock) {
        return MockFactory.createOptionDetail("od", 1000, stock);
    }
    @Test
    @DisplayName("재고량이 100개인 옵션의 재고를 2개 감소시킨다")
    void decrement_when_success() {
        // given
        final var optionDetail = initOptionDetail(100);
        given(optionDetailRepository.findById(anyString()))
                .willReturn(Optional.of(optionDetail));
        // when
        var result = service.decrement("id", 2).getOrNull();
        // then
        assertNotNull(result);
        assertEquals(optionDetail, result);
        assertEquals(98, result.getStock());
    }

    @Test
    @DisplayName("재고량이 0개인 옵션의 재고를 2개 증가시킨다")
    void increment_when_success() {
        // given
        final var optionDetail = initOptionDetail(0);
        given(optionDetailRepository.findById(anyString()))
                .willReturn(Optional.of(optionDetail));
        // when
        var result = service.increment("id", 2).getOrNull();
        // then
        assertNotNull(result);
        assertEquals(optionDetail, result);
        assertEquals(2, result.getStock());
    }

    @Test
    @DisplayName("존재하지 않는 옵션의 재고를 감소시킬 수 없다")
    void decrement_when_noResultOptionDetail() {
        // given
        given(optionDetailRepository.findById(anyString()))
                .willReturn(Optional.empty());
        // when
        // then
        assertThrows(
                ResourceNotFoundException.class,
                () -> service.decrement("od", 2).getOrThrow()
        );
    }

    @Test
    @DisplayName("존재하지 않는 옵션의 재고를 증가시킬 수 없다")
    void increment_when_noResultoptionDetail() {
        // given
        given(optionDetailRepository.findById(anyString()))
                .willReturn(Optional.empty());
        // when
        // then
        assertThrows(
                ResourceNotFoundException.class,
                () -> service.increment("od", 2).getOrThrow()
        );
    }

    @Test
    @DisplayName("재고량이 1개인 옵션의 재고를 2개 감소시킬 수 없다")
    void decrement_when_noMoreStock() {
        // given
        final var od = initOptionDetail(1);
        given(optionDetailRepository.findById(anyString()))
                .willReturn(Optional.of(od));
        // when
        // then
        assertThrows(
                BadRequestException.class,
                () -> service.decrement("od", 2).getOrThrow()
        );
    }
}
