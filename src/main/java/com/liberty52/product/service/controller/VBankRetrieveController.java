package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.VBankRetrieveService;
import com.liberty52.product.service.controller.dto.VBankRetrieve;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "가상계좌", description = "가상계좌 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class VBankRetrieveController {

    private final VBankRetrieveService vBankRetrieveService;

    @Operation(summary = "가상계좌 목록 조회", description = "등록된 가상계좌 목록을 조회합니다.")
    @GetMapping("/vbanks")
    @ResponseStatus(HttpStatus.OK)
    public VBankRetrieve.ListResponse getVBankInfoList() {
        return VBankRetrieve.ListResponse.fromDtoList(
                vBankRetrieveService.getVBankList()
        );
    }

}
