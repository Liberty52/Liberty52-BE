package com.liberty52.auth.global.oauth2;


import java.util.Map;

import static org.springframework.util.ObjectUtils.isEmpty;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {

  private final Map<String, Object> response;
  String NAVER_ATTRIBUET_KEY = "response";
  String NAVER_PHONE_NUMBER_KEY = "mobile";
  String NAVER_ID_KEY = "id";
  String NAVER_NAME_KEY = "name";
  String NAVER_PROFILE_IMAGE_URL_KEY = "profile_image";
  String NAVER_EMAIL_KEY = "email";
  public NaverOAuth2UserInfo(Map<String, Object> attributes) {
    super(attributes);

    response = (Map<String, Object>) attributes.get(NAVER_ATTRIBUET_KEY);
  }

  @Override
  public String getId() {
    if (isEmpty(response))
      return null;
    return (String) response.get(NAVER_ID_KEY);
  }

  @Override
  public String getPhoneNumber() {
    if (isEmpty(response))
      return null;
    return (String) response.get(NAVER_PHONE_NUMBER_KEY);
  }

  @Override
  public String getImageUrl() {
    if (isEmpty(response))
      return null;
    return (String) response.get(NAVER_PROFILE_IMAGE_URL_KEY);
  }

  @Override
  public String getEmail() {
    if (isEmpty(response))
      return null;
    return (String) response.get(NAVER_EMAIL_KEY);
  }

  @Override
  public String getName() {
    if (isEmpty(response))
      return null;
    return (String) response.get(NAVER_NAME_KEY);
  }
}
