package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.OptionDetailModifyService;
import com.liberty52.product.service.controller.dto.OptionDetailModifyRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Tag(name = "옵션 상세", description = "옵션 상세 관련 API를 제공합니다")
public class OptionDetailModifyController {

    private final OptionDetailModifyService optionDetailModifyService;

    @PutMapping("/admin/optionDetail/{optionDetailId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "옵션 상세 수정", description = "관리자 권한으로 옵션 상세를 수정합니다.")
    public void modifyOptionDetailByAdmin(@RequestHeader("LB-Role") String role, @PathVariable String optionDetailId, @Validated @RequestBody OptionDetailModifyRequestDto dto) {
        optionDetailModifyService.modifyOptionDetailByAdmin(role, optionDetailId, dto);
    }

    @PutMapping("/admin/optionDetailOnSale/{optionDetailId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "옵션 상세 판매 상태 수정", description = "관리자 권한으로 옵션 상세의 판매 상태를 수정합니다.")
    public void modifyOptionDetailOnSailStateByAdmin(@RequestHeader("LB-Role") String role, @PathVariable String optionDetailId) {
        optionDetailModifyService.modifyOptionDetailOnSailStateByAdmin(role, optionDetailId);
    }
}
