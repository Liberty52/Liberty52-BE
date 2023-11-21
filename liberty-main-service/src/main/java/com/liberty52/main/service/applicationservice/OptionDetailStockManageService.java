package com.liberty52.main.service.applicationservice;

import com.liberty52.main.global.util.Result;
import com.liberty52.main.service.entity.OptionDetail;

public interface OptionDetailStockManageService {

    Result<OptionDetail> decrement(String optionDetailId, int quantity);

    Result<OptionDetail> increment(String optionDetailId, int quantity);

    Result<OptionDetail> rollback(String optionDetailId, int quantity);
}
