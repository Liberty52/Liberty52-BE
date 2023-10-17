package com.liberty52.product.service.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.liberty52.product.service.applicationservice.DeliveryOptionModifyService;
import com.liberty52.product.service.applicationservice.DeliveryOptionRetrieveService;
import com.liberty52.product.service.controller.dto.DeliveryOptionFeeModify;
import com.liberty52.product.service.controller.dto.DeliveryOptionFeeRetrieve;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "배송", description = "배송 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
public class DeliveryOptionController {

	private final DeliveryOptionRetrieveService deliveryOptionRetrieveService;
	private final DeliveryOptionModifyService deliveryOptionModifyService;

	@Operation(summary = "기본 배송비 조회", description = "기본 배송비를 조회합니다.")
	@GetMapping("/options/delivery/fee")
	@ResponseStatus(HttpStatus.OK)
	public DeliveryOptionFeeRetrieve.Response getDefaultDeliveryFee() {
		return DeliveryOptionFeeRetrieve.Response.fromDto(
			deliveryOptionRetrieveService.getDefaultDeliveryFee()
		);
	}

	@Operation(summary = "기본 배송비 수정", description = "관리자 권한을 사용하여 기본 배송비를 수정합니다.")
	@PatchMapping("/admin/options/delivery/fee")
	@ResponseStatus(HttpStatus.OK)
	public DeliveryOptionFeeModify.Response updateDefaultDeliveryFeeByAdmin(
		@RequestHeader(HttpHeaders.AUTHORIZATION) String adminId,
		@RequestHeader("LB-Role") String role,
		@RequestBody @Valid DeliveryOptionFeeModify.Request request) {
		return DeliveryOptionFeeModify.Response.fromDto(
			deliveryOptionModifyService.updateDefaultDeliveryFeeByAdmin(role, request.getFee())
		);
	}
}
