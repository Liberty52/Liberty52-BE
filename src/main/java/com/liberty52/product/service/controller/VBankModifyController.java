package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.VBankModifyService;
import com.liberty52.product.service.controller.dto.VBankModify;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "가상계좌", description = "가상계좌 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class VBankModifyController {

    private final VBankModifyService vBankModifyService;

    @Operation(summary = "가상계좌 수정", description = "관리자가 가상계좌 정보를 수정합니다.")
    @PutMapping("/admin/vbanks/{vBankId}")
    @ResponseStatus(HttpStatus.OK)
    public VBankModify.Response updateVBankByAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String adminId,
                                                   @RequestHeader("LB-Role") String role,
                                                   @PathVariable String vBankId,
                                                   @RequestBody @Valid VBankModify.Request request) {
        return VBankModify.Response.fromDto(
                vBankModifyService.updateVBankByAdmin(role, vBankId, request.getBank(), request.getAccountNumber(), request.getHolder())
        );
    }
}
