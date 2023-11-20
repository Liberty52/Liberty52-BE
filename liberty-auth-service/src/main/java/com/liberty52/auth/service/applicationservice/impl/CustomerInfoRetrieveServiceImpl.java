package com.liberty52.auth.service.applicationservice.impl;

import com.liberty52.auth.global.utils.AdminRoleUtils;
import com.liberty52.auth.service.applicationservice.CustomerInfoRetrieveService;
import com.liberty52.auth.service.controller.dto.CustomerInfoListResponseDto;
import com.liberty52.auth.service.entity.Auth;
import com.liberty52.auth.service.entity.Role;
import com.liberty52.auth.service.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerInfoRetrieveServiceImpl implements CustomerInfoRetrieveService {
    private final AuthRepository authRepository;

    @Override
    public CustomerInfoListResponseDto retrieveCustomerInfoByAdmin(String role, Pageable pageable) {
        AdminRoleUtils.checkRole(role);
        Page<Auth> page = authRepository.findByRole(Role.USER, pageable);
        return CustomerInfoListResponseDto.of(page);
    }
}
