package com.liberty52.auth.global.oauth2;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class GoogleOAuth2UserInfo extends OAuth2UserInfo{

    private static final String GOOGLE_EMAIL_KEY = "email";
    private static final String GOOGLE_PICTURE_URL_KEY = "picture";
    private static final String GOOGLE_NAME_KEY = "name";
    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getPhoneNumber() {
        return null;
    }

    @Override
    public String getImageUrl() {

        return (String) attributes.get(GOOGLE_PICTURE_URL_KEY);
    }

    @Override
    public String getEmail() {

        return (String) attributes.get(GOOGLE_EMAIL_KEY);
    }

    @Override
    public String getName() {

        return (String) attributes.get(GOOGLE_NAME_KEY);
    }
}
