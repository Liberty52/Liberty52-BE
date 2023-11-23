package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.main.global.util.Result;
import com.liberty52.main.service.applicationservice.LicenseOptionDetailStockManageService;
import com.liberty52.main.service.applicationservice.OptionDetailMultipleStockManageService;
import com.liberty52.main.service.applicationservice.OptionDetailStockManageService;
import com.liberty52.main.service.entity.OptionDetail;
import com.liberty52.main.service.entity.license.LicenseOptionDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionDetailMultipleStockManageServiceImpl implements OptionDetailMultipleStockManageService {

    private final OptionDetailStockManageService optionDetailStockManageService;
    private final LicenseOptionDetailStockManageService licenseOptionDetailStockManageService;


    /**
     * 해당 메소드는 DistributedLockTransaction을 통해 각각의 옵션 디테일 별로 트랜잭션이 생성됨에 따라
     * 서로 다른 옵션 디테일 간의 트랜잭션을 코드 레벨에서 하나로 묶어서 예외 발생 시 롤백 시키기 위함이다. <br>
     * 예를 들어, 옵션1, 옵션2, 옵션3에 대한 재고량을 감소시키던 도중에 옵션3의 재고량이 부족하여 예외가 발생하더라도
     * 옵션1, 옵션2는 서로 다른 트랜잭션에 의해 update 커밋이 발생된 이후이기 때문에, 옵션1과 옵션2의 재고량이 감소되어 있다. <br>
     * 따라서 예외 발생 시 재고량이 0이 아닌 옵션에 대해서만 롤백시키는 작업을 수행해야 한다.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result<List<OptionDetail>> decrement(List<String> optionDetailIds, int quantity) {
        return Result.runCatching(() -> {
            List<OptionDetail> rollbackList = new ArrayList<>();
            try {
                return optionDetailIds.stream()
                        .map(it -> {
                            var optionDetail = optionDetailStockManageService.decrement(it, quantity).getOrThrow();
                            rollbackList.add(optionDetail);
                            return optionDetail;
                        })
                        .toList();
            } catch (Exception e) {
                rollbackList.forEach(it -> optionDetailStockManageService.rollback(it.getId(), quantity).getOrThrow());
                throw e;
            }
        });
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result<LicenseOptionDetail> decrementLicense(String licenseOptionDetailId, int quantity) {
        return Result.runCatching(() -> {
            List<LicenseOptionDetail> rollbackList = new ArrayList<>();
            try {
                var licenseOptionDetail = licenseOptionDetailStockManageService.decrement(licenseOptionDetailId, quantity).getOrThrow();
                rollbackList.add(licenseOptionDetail);
                return licenseOptionDetail;
            } catch (Exception e) {
                rollbackList.forEach(
                    it -> licenseOptionDetailStockManageService.rollback(it.getId(), quantity).getOrThrow());
                throw e;
            }
        });

    }
}
