package com.liberty52.auth.user.web.rest;

import com.liberty52.auth.user.service.CustomerInfoRetrieveService;
import com.liberty52.auth.user.web.dto.CustomerInfoListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerInfoRetrieveController {
    private final CustomerInfoRetrieveService customerInfoRetrieveService;

    @GetMapping("/admin/customer-info")
    @ResponseStatus(HttpStatus.OK)
    public CustomerInfoListResponseDto retrieveCustomerInfoByAdmin(@RequestHeader("LB-Role") String role,
                                                               Pageable pageable) {
        return customerInfoRetrieveService.retrieveCustomerInfoByAdmin(role, pageable);
    }
}
