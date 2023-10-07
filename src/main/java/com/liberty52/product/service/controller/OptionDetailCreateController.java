package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.OptionDetailCreateService;
import com.liberty52.product.service.controller.dto.CreateOptionDetailRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "옵션 상세", description = "옵션 상세 관련 API를 제공합니다")
public class OptionDetailCreateController {

    private final OptionDetailCreateService optionDetailCreateService;

    @PostMapping("/admin/optionDetail/{optionId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "옵션 상세 생성", description = "관리자 권한으로 옵션 상세를 생성합니다.")
    public void createOptionDetailByAdmin(@RequestHeader("LB-Role") String role,
                                   @Validated @RequestBody CreateOptionDetailRequestDto dto, @PathVariable String optionId) {
        optionDetailCreateService.createOptionDetailByAdmin(role, dto, optionId);
    }
}
