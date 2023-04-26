package com.liberty52.product.global.adapter.cloud;

import com.liberty52.product.global.adapter.cloud.dto.AuthProfileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "auth", contextId = "authServiceClient", primary = false)
public interface AuthServiceClient {

    @GetMapping(value = "/my")
    AuthProfileDto getAuthProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId);

}
