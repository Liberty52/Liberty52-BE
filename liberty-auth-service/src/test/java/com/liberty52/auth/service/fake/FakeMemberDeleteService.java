package com.liberty52.auth.service.fake;

import com.liberty52.auth.service.applicationservice.MemberDeleteService;
import com.liberty52.auth.service.repository.AuthRepository;

public class FakeMemberDeleteService implements MemberDeleteService {

    private final AuthRepository authRepository;

    public FakeMemberDeleteService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public void deleteMemberByUserId(String userId) {
        authRepository.deleteById(userId);
    }
}
