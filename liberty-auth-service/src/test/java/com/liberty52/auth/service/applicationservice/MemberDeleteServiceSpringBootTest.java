package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.service.utils.MockFactory;
import com.liberty52.auth.user.entity.Auth;
import com.liberty52.auth.user.repository.AuthRepository;
import com.liberty52.auth.user.service.MemberDeleteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MemberDeleteServiceSpringBootTest {

    @Autowired
    MemberDeleteService memberDeleteService;

    @Autowired
    AuthRepository authRepository;
    String userId;

    @BeforeEach
    void beforeEach(){
        Auth auth = MockFactory.createMockAuth();
        authRepository.save(auth);

        userId = auth.getId();
    }

    @Test
    void deleteByUserId () throws Exception{
        //given
        memberDeleteService.deleteMemberByUserId(userId);
        //when
        Optional<Auth> result_isNotPresent = authRepository.findById(userId);
        //then
        assertThat(result_isNotPresent).isNotPresent();

    }
}
