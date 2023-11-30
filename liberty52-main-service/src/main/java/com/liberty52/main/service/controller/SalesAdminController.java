package com.liberty52.main.service.controller;


import com.liberty52.main.service.applicationservice.SalesRetrieveService;
import com.liberty52.main.service.controller.dto.SalesRequestDto;
import com.liberty52.main.service.controller.dto.SalesResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "매출", description = "매출 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class SalesAdminController {

    private final SalesRetrieveService salesRetrieveService;

    @Operation(summary = "매출 조회", description = "조건에 맞는 매출과 판매량을 조회합니다.")
    @PostMapping("/admin/sales")
    public ResponseEntity<SalesResponseDto> retrieveSalesByAdmin(@RequestHeader("LB-Role") String role, @RequestBody SalesRequestDto salesRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(salesRetrieveService.retrieveSales(role, salesRequestDto));
    }
}
