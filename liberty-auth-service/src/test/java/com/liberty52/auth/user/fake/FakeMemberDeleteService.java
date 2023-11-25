package com.liberty52.auth.user.fake;

import com.liberty52.auth.user.repository.AuthRepository;
import com.liberty52.auth.user.service.MemberDeleteService;

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
