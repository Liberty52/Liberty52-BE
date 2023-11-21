package com.liberty52.main.service.applicationservice;

import com.liberty52.main.global.util.Result;
import com.liberty52.main.service.entity.OptionDetail;
import com.liberty52.main.service.entity.license.LicenseOptionDetail;

import java.util.List;

public interface OptionDetailMultipleStockManageService {
    Result<List<OptionDetail>> decrement(List<String> optionDetailIds, int quantity);

    Result<List<LicenseOptionDetail>> decrementLicense(List<String> licenseOptionDetailIds, int quantity);
}
