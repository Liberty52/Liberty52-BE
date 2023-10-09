package com.liberty52.product.service.applicationservice;

import com.liberty52.product.global.util.Result;
import com.liberty52.product.service.entity.OptionDetail;

/**
 * 옵션 디테일의 재고량을 관리(증가, 감소)하기 위한 서버 내부용 서비스 인터페이스
 */
public interface OptionDetailStockManageService {

    Result<OptionDetail> decrement(String optionDetailId, int quantity);

    Result<OptionDetail> increment(String optionDetailId, int quantity);
}
