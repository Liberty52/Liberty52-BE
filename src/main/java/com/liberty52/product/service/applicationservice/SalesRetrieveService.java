package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.controller.dto.SalesRequestDto;
import com.liberty52.product.service.controller.dto.SalesResponseDto;

public interface SalesRetrieveService {
    public SalesResponseDto retrieveSales(String role, SalesRequestDto salesRequestDto);
}
