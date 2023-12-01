package com.liberty52.auth.user.service;

import com.liberty52.auth.user.entity.Auth;
import com.liberty52.auth.user.repository.AuthRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Base64;

import static com.liberty52.auth.user.utils.MockConstants.*;

@SpringBootTest
class PasswordMailServiceImplTestImpl {


    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private PasswordMailService passwordMailService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    private String email;

    @BeforeEach
    void initAuth(){
        Auth auth = Auth.createUser(
                "hsh47607@daum.net",
                MOCK_USER_PASSWORD,
                MOCK_USER_NAME,
                MOCK_PHONE_NUMBER,
                MOCK_PROFILE_URL);
        authRepository.save(auth);
        email = auth.getEmail();
    }

    @AfterEach
    void deleteAuth() {
        Auth auth = authRepository.findByEmail(email).get();
        authRepository.delete(auth);
    }

    @Test
    void test_sendMailForUpdatePassword() throws Exception {
        Assertions.assertTrue(() -> passwordMailService.sendEmailForUpdatePassword(email));
    }

    @Test
    void test_updatePassword() throws Exception {
        String emailToken = new String(Base64.getEncoder().encode(email.getBytes()));

        String updatePassword = "test1234";
        passwordMailService.updatePassword(emailToken, updatePassword);

        Auth auth = authRepository.findByEmail(email).get();
        String password = auth.getPassword();

        Assertions.assertAll(
                () -> Assertions.assertNotNull(auth),
                () -> Assertions.assertTrue(passwordEncoder.matches(updatePassword, password))
        );
    }


}
