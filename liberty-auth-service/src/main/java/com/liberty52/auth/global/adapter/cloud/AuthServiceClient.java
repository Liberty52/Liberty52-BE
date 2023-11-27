package com.liberty52.auth.global.adapter.cloud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "auth", primary = false, url = "${auth.svc.path}")
public interface AuthServiceClient {

    @GetMapping("/user-infos/writer-ids")
    ResponseEntity<Map<String, String>> getUserInfosByWriterIds(@RequestParam List<String> writerIds);

}
