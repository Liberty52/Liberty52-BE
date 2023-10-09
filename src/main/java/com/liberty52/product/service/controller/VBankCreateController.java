package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.VBankCreateService;
import com.liberty52.product.service.controller.dto.VBankCreate;
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
public class VBankCreateController {

    private final VBankCreateService vBankCreateService;

    @Operation(summary = "가상계좌 생성", description = "관리자가 가상계좌를 생성합니다.")
    @PostMapping("/admin/vbanks")
    @ResponseStatus(HttpStatus.CREATED)
    public VBankCreate.Response createVBankByAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String adminId,
                                                   @RequestHeader("LB-Role") String role,
                                                   @RequestBody @Valid VBankCreate.Request request) {
        return VBankCreate.Response.fromDto(
            vBankCreateService.createVBankByAdmin(role, request.getBank(), request.getAccountNumber(), request.getHolder())
        );
    }

}
