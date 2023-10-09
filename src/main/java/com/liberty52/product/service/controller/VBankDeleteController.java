package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.VBankDeleteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "가상계좌", description = "가상계좌 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class VBankDeleteController {

    private final VBankDeleteService vBankDeleteService;

    @Operation(summary = "가상계좌 삭제", description = "관리자가 가상계좌를 삭제합니다.")
    @DeleteMapping("/admin/vbanks/{vBankId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVBankByAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String adminId,
                                   @RequestHeader("LB-Role") String role,
                                   @PathVariable String vBankId) {
        vBankDeleteService.deleteVBankByAdmin(role, vBankId);
    }

}
