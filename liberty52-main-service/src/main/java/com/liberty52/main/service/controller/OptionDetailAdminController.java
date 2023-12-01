package com.liberty52.main.service.controller;

import com.liberty52.main.service.applicationservice.OptionDetailCreateService;
import com.liberty52.main.service.applicationservice.OptionDetailModifyService;
import com.liberty52.main.service.controller.dto.CreateOptionDetailRequestDto;
import com.liberty52.main.service.controller.dto.OptionDetailModifyRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "상품", description = "상품 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class OptionDetailAdminController {

    private final OptionDetailCreateService optionDetailCreateService;
    private final OptionDetailModifyService optionDetailModifyService;

    @Operation(summary = "옵션 상세 생성", description = "관리자가 새로운 옵션 상세를 생성하는 엔드포인트입니다.")
    @PostMapping("/admin/optionDetail/{optionId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOptionDetailByAdmin(@RequestHeader("LB-Role") String role,
                                          @Validated @RequestBody CreateOptionDetailRequestDto dto, @PathVariable String optionId) {
        optionDetailCreateService.createOptionDetailByAdmin(role, dto, optionId);
    }

    @Operation(summary = "옵션 상세 수정", description = "관리자가 옵션 상세 정보를 수정하는 엔드포인트입니다.")
    @PutMapping("/admin/optionDetail/{optionDetailId}")
    @ResponseStatus(HttpStatus.OK)
    public void modifyOptionDetailByAdmin(
            @RequestHeader("LB-Role") String role,
            @PathVariable String optionDetailId,
            @Validated @RequestBody OptionDetailModifyRequestDto dto
    ) {
        optionDetailModifyService.modifyOptionDetailByAdmin(role, optionDetailId, dto);
    }

    @Operation(summary = "옵션 상세 판매 상태 수정", description = "관리자가 옵션 상세의 판매 상태를 수정하는 엔드포인트입니다.")
    @PutMapping("/admin/optionDetailOnSale/{optionDetailId}")
    @ResponseStatus(HttpStatus.OK)
    public void modifyOptionDetailOnSailStateByAdmin(
            @RequestHeader("LB-Role") String role,
            @PathVariable String optionDetailId
    ) {
        optionDetailModifyService.modifyOptionDetailOnSailStateByAdmin(role, optionDetailId);
    }
}
