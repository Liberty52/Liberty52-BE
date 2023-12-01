package com.liberty52.auth.user.utils;

import com.liberty52.auth.user.entity.Auth;
import com.liberty52.auth.user.entity.SocialLogin;
import com.liberty52.auth.user.entity.SocialLoginType;

public class MockFactory {


    public static Auth createMockAuth(){
        return Auth.createUser(MockConstants.MOCK_USER_EMAIL,
                MockConstants.MOCK_USER_PASSWORD,
                MockConstants.MOCK_USER_NAME,
                MockConstants.MOCK_PHONE_NUMBER,
                MockConstants.MOCK_PROFILE_URL);
    }

    public static SocialLogin createSocialLogin(SocialLoginType socialLoginType){
        return SocialLogin.builder()
                .type(socialLoginType)
                .email(MockConstants.MOCK_USER_EMAIL)
                .build();
    }

}
