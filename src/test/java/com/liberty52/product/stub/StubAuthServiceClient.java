package com.liberty52.product.stub;

import com.liberty52.product.global.adapter.cloud.AuthServiceClient;
import com.liberty52.product.global.adapter.cloud.dto.AuthProfileDto;

public class StubAuthServiceClient implements AuthServiceClient {
    @Override
    public AuthProfileDto getAuthProfile(String authId) {
        return new AuthProfileDto("mju.omnm@gmail.com", "빌드 테스터");
    }
}
