package com.liberty52.auth.service.repository;

import com.liberty52.auth.user.entity.Auth;
import com.liberty52.auth.user.entity.SocialLoginType;
import com.liberty52.auth.user.repository.AuthRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.liberty52.auth.service.utils.MockConstants.MOCK_USER_EMAIL;
import static com.liberty52.auth.service.utils.MockFactory.createMockAuth;
import static com.liberty52.auth.service.utils.MockFactory.createSocialLogin;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class AuthRepositoryTest {

    @Autowired
    AuthRepository authRepository;
    @Autowired
    EntityManager em;

    Auth auth;

    @BeforeEach
    void beforeEach(){
        auth = createMockAuth();
    }
    @AfterEach
    void afterEach(){
        em.clear();
    }

    @Test
    void findAuthAndSocialLoginByEmail () throws Exception{
        //given
        auth.addSocialLogin(createSocialLogin(SocialLoginType.FACEBOOK));
        auth.addSocialLogin(createSocialLogin(SocialLoginType.NAVER));
        authRepository.save(auth);

        //when
        Auth finded = authRepository.findAuthAndSocialLoginByEmail(MOCK_USER_EMAIL)
                .get();

        //then
        assertThat(finded.isRegisteredSocialLoginType(SocialLoginType.FACEBOOK)).isTrue();
        assertThat(finded.isRegisteredSocialLoginType(SocialLoginType.NAVER)).isTrue();

    }

}