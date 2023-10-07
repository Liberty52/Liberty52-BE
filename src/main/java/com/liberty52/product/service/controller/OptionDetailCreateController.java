package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.OptionDetailCreateService;
import com.liberty52.product.service.controller.dto.CreateOptionDetailRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "옵션 상세", description = "옵션 상세 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class OptionDetailCreateController {

    private final OptionDetailCreateService optionDetailCreateService;

    @Operation(summary = "옵션 상세 생성", description = "관리자가 새로운 옵션 상세를 생성하는 엔드포인트입니다.")
    @PostMapping("/admin/optionDetail/{optionId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOptionDetailByAdmin(@RequestHeader("LB-Role") String role,
                                   @Validated @RequestBody CreateOptionDetailRequestDto dto, @PathVariable String optionId) {
        optionDetailCreateService.createOptionDetailByAdmin(role, dto, optionId);
    }
}
