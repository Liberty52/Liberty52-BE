package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.common.exception.external.badrequest.BadRequestException;
import com.liberty52.main.global.annotation.DistributedLock;
import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.global.util.Result;
import com.liberty52.main.service.applicationservice.OptionDetailStockManageService;
import com.liberty52.main.service.entity.OptionDetail;
import com.liberty52.main.service.repository.OptionDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OptionDetailStockManageServiceImpl implements OptionDetailStockManageService {

    private final OptionDetailRepository optionDetailRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @DistributedLock(key = "#optionDetailId")
    public Result<OptionDetail> decrement(String optionDetailId, int quantity) {
        return Result.runCatching(() -> {
            var optionDetail = this.findById(optionDetailId);
            optionDetail.sold(quantity)
                    .orElseThrow(() -> new BadRequestException(optionDetail.getName() + " 옵션의 재고량이 부족하여 구매할 수 없습니다."));
            return optionDetail;
        });
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @DistributedLock(key = "#optionDetailId")
    public Result<OptionDetail> increment(String optionDetailId, int quantity) {
        return Result.runCatching(() -> {
            var optionDetail = this.findById(optionDetailId);
            optionDetail.rollbackStock(quantity);
            return optionDetail;
        });
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @DistributedLock(key = "#optionDetailId")
    public Result<OptionDetail> rollback(String optionDetailId, int quantity) {
        return Result.runCatching(() -> {
            var optionDetail = this.findById(optionDetailId);
            if (optionDetail.getStock() > 0) {
                optionDetail.rollbackStock(quantity);
            }
            return optionDetail;
        });
    }

    private OptionDetail findById(String id) {
        return optionDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("option detail", "id", id));
    }
}
