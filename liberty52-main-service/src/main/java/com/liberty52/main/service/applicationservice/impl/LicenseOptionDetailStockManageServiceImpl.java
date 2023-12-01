package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.common.exception.external.badrequest.BadRequestException;
import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.global.annotation.DistributedLock;
import com.liberty52.main.global.util.Result;
import com.liberty52.main.service.applicationservice.LicenseOptionDetailStockManageService;
import com.liberty52.main.service.entity.license.LicenseOptionDetail;
import com.liberty52.main.service.repository.LicenseOptionDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LicenseOptionDetailStockManageServiceImpl implements LicenseOptionDetailStockManageService {
    private final LicenseOptionDetailRepository licenseOptionDetailRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @DistributedLock(key = "#licenseOptionDetailId")
    public Result<LicenseOptionDetail> decrement(String licenseOptionDetailId, int quantity) {
        return Result.runCatching(() -> {
            var licenseOptionDetail = this.findById(licenseOptionDetailId);
            licenseOptionDetail.sold(quantity)
                .orElseThrow(() -> new BadRequestException(licenseOptionDetail.getArtName() + " 옵션의 재고량이 부족하여 구매할 수 없습니다."));
            return licenseOptionDetail;
        });
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @DistributedLock(key = "#licenseOptionDetailId")
    public Result<LicenseOptionDetail> increment(String licenseOptionDetailId, int quantity) {
        return Result.runCatching(() -> {
            var licenseOptionDetail = this.findById(licenseOptionDetailId);
            licenseOptionDetail.rollbackStock(quantity);
            return licenseOptionDetail;
        });
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @DistributedLock(key = "#licenseOptionDetailId")
    public Result<LicenseOptionDetail> rollback(String licenseOptionDetailId, int quantity) {
        return Result.runCatching(() -> {
            var licenseOptionDetail = this.findById(licenseOptionDetailId);
            if (licenseOptionDetail.getStock() > 0) {
                licenseOptionDetail.rollbackStock(quantity);
            }
            return licenseOptionDetail;
        });
    }

    private LicenseOptionDetail findById(String id) {
        return licenseOptionDetailRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("license option detail", "id", id));
    }
}
