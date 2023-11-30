package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.service.applicationservice.LicenseOptionDetailStockManageService;
import com.liberty52.main.service.applicationservice.OptionDetailStockManageService;
import com.liberty52.main.service.applicationservice.OrderFailedPayRollbackService;
import com.liberty52.main.service.entity.CustomProductOption;
import com.liberty52.main.service.entity.Orders;
import com.liberty52.main.service.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderFailedPayRollbackServiceImpl implements OrderFailedPayRollbackService {

    private final OptionDetailStockManageService optionDetailStockManageService;
    private final LicenseOptionDetailStockManageService licenseOptionDetailStockManageService;
    private final OrdersRepository ordersRepository;

    @Override
    @Transactional
    public void rollback(String orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("order", "id", orderId));

        order.getCustomProducts().forEach(cp -> {
            if(cp.getProduct().isCustom()){
                cp.getOptions().stream()
                    .map(CustomProductOption::getOptionDetail)
                    .forEach(it -> optionDetailStockManageService.increment(it.getId(), cp.getQuantity()));
            }else{
                licenseOptionDetailStockManageService.increment(cp.getCustomLicenseOption().getLicenseOptionDetail().getId(), cp.getQuantity());
            }

        });
    }

}
