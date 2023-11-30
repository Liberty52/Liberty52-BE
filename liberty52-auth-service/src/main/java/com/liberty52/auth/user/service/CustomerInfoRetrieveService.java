package com.liberty52.auth.user.service;

import com.liberty52.auth.user.web.dto.CustomerInfoListResponseDto;
import org.springframework.data.domain.Pageable;

public interface CustomerInfoRetrieveService {
    CustomerInfoListResponseDto retrieveCustomerInfoByAdmin(String role, Pageable pageable);
}
