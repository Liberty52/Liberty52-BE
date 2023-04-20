package com.liberty52.product.global.adapter;

import com.liberty52.product.service.controller.dto.AuthClientDataResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("auth")
public interface AuthClient {

//    @PostMapping("/auth/")
    Map<String, AuthClientDataResponse> retrieveAuthData(@RequestBody Set<String> ids);

}
