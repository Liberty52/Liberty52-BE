package com.liberty52.main.service.applicationservice;

import com.liberty52.main.global.util.Result;
import com.liberty52.main.service.entity.license.LicenseOptionDetail;

public interface LicenseOptionDetailStockManageService {

    Result<LicenseOptionDetail> decrement(String optionDetailId, int quantity);

    Result<LicenseOptionDetail> increment(String optionDetailId, int quantity);

    Result<LicenseOptionDetail> rollback(String optionDetailId, int quantity);
}
