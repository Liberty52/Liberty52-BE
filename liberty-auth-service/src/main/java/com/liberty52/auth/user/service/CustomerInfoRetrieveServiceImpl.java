package com.liberty52.auth.user.service;

import com.liberty52.auth.global.utils.AdminRoleUtils;
import com.liberty52.auth.user.entity.Auth;
import com.liberty52.auth.user.entity.Role;
import com.liberty52.auth.user.repository.AuthRepository;
import com.liberty52.auth.user.web.dto.CustomerInfoListResponseDto;
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
        AdminRoleUtils.isAdmin(role);
        Page<Auth> page = authRepository.findByRole(Role.USER, pageable);
        return CustomerInfoListResponseDto.of(page);
    }
}
