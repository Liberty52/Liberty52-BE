package com.liberty52.product.service.applicationservice;

import com.liberty52.product.global.util.Result;
import com.liberty52.product.service.entity.OptionDetail;

import java.util.List;

public interface OptionDetailMultipleStockManageService {
    Result<List<OptionDetail>> decrement(List<String> optionDetailIds, int quantity);
}
