package com.liberty52.auth.global.oauth2;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
public class FacebookOAuth2UserInfo extends OAuth2UserInfo {

    private static final String FACEBOOK_NAME_KEY = "name";
    private static final String FACEBOOK_EMAIL_KEY = "email";
    private static final String PICTURE_URL_KEY = "url";
    private final Map<String, Object> picture;
    public FacebookOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
        log.info("attribute = {}", attributes);
        picture = (Map<String, Object>) attributes.get("picture");
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
        if(isEmpty(picture))
            return null;

        return (String) picture.get(PICTURE_URL_KEY);
    }

    @Override
    public String getEmail() {
        return (String) attributes.get(FACEBOOK_EMAIL_KEY);
    }

    @Override
    public String getName() {
        return (String) attributes.get(FACEBOOK_NAME_KEY);
    }
}
