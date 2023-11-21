package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.service.fake.FakeAuthRepository;
import com.liberty52.auth.service.fake.FakeMemberDeleteService;
import com.liberty52.auth.user.entity.Auth;
import com.liberty52.auth.user.repository.AuthRepository;
import com.liberty52.auth.user.service.MemberDeleteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.liberty52.auth.service.utils.MockFactory.createMockAuth;
import static org.assertj.core.api.Assertions.assertThat;

class MemberDeleteServiceMockTest {

    AuthRepository authRepository;
    MemberDeleteService memberDeleteService;

    String mockUserId;

    @BeforeEach
    void setMockUserData(){
        authRepository = new FakeAuthRepository();
        memberDeleteService = new FakeMemberDeleteService(authRepository);

        // save Auth Entity
        Auth auth = createMockAuth();
        mockUserId = authRepository.save(auth).getId();
    }
    // Controller에서 UserId를 받았을 때 삭제
    @Test
    void deleteByUserId  () throws Exception{
        //given
        memberDeleteService.deleteMemberByUserId(mockUserId);
        //when
        Optional<Auth> result = authRepository.findById(mockUserId);
        //then
        assertThat(result).isNotPresent();
    }
}