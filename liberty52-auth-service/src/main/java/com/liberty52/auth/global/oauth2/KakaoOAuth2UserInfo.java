package com.liberty52.auth.global.oauth2;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
public class KakaoOAuth2UserInfo extends OAuth2UserInfo{

    private static final String KAKAO_ACCOUNT_KEY = "kakao_account";
    private static final String KAKAO_PROFILE_KEY = "profile";
    private static final String KAKAO_PROFILE_IMAGE_URL_KEY = "profile_image_url";
    private static final String KAKAO_ACCOUNT_EMAIL_KEY = "email";
    private static final String KAKAO_PROFILE_NICKNAME_KEY = "nickname";
    private final Map<String, Object> kakaoAccount;
    private final Map<String, Object> kakaoProfile;


    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
        // kakao는 kakao_account에 유저정보가 있다. (email)
        kakaoAccount = (Map<String, Object>)attributes.get(KAKAO_ACCOUNT_KEY);
        // kakao_account안에 또 profile이라는 JSON객체가 있다. (nickname, profile_image)
        kakaoProfile = (Map<String, Object>)kakaoAccount.get(KAKAO_PROFILE_KEY);
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getPhoneNumber() {
        return null; // TODO Kakao Sink 검수 완료해야 권한 획득 가능
    }

    @Override
    public String getImageUrl() {
        if(isEmpty(kakaoProfile))
            return null;

        return (String) kakaoProfile.get(KAKAO_PROFILE_IMAGE_URL_KEY);
    }

    @Override
    public String getEmail() {
        if(isEmpty(kakaoAccount))
            return null;

        return (String) kakaoAccount.get(KAKAO_ACCOUNT_EMAIL_KEY);
    }

    @Override
    public String getName() {
        if(isEmpty(kakaoProfile))
            return null;

        return (String) kakaoProfile.get(KAKAO_PROFILE_NICKNAME_KEY);
    }
}
