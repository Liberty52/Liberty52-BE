package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.SalesRequestDto;
import com.liberty52.main.service.controller.dto.SalesResponseDto;

public interface SalesRetrieveService {
    SalesResponseDto retrieveSales(String role, SalesRequestDto salesRequestDto);
}
