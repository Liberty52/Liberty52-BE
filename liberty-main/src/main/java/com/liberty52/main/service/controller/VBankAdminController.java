package com.liberty52.main.service.controller;

import com.liberty52.main.service.applicationservice.VBankCreateService;
import com.liberty52.main.service.applicationservice.VBankDeleteService;
import com.liberty52.main.service.applicationservice.VBankModifyService;
import com.liberty52.main.service.controller.dto.VBankCreate;
import com.liberty52.main.service.controller.dto.VBankModify;
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
public class VBankAdminController {

    private final VBankCreateService vBankCreateService;
    private final VBankModifyService vBankModifyService;
    private final VBankDeleteService vBankDeleteService;


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

    @Operation(summary = "가상계좌 삭제", description = "관리자가 가상계좌를 삭제합니다.")
    @DeleteMapping("/admin/vbanks/{vBankId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVBankByAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String adminId,
                                   @RequestHeader("LB-Role") String role,
                                   @PathVariable String vBankId) {
        vBankDeleteService.deleteVBankByAdmin(role, vBankId);
    }

}
