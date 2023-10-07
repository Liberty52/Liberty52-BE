package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.DeliveryOptionRetrieveService;
import com.liberty52.product.service.controller.dto.DeliveryOptionFeeRetrieve;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "배송 정보", description = "배송 정보 관련 API를 제공합니다.")
public class DeliveryOptionRetrieveController {

    private final DeliveryOptionRetrieveService deliveryOptionRetrieveService;

    @GetMapping("/options/delivery/fee")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "기본 배송비 조회", description = "기본 배송비를 조회합니다.")
    public DeliveryOptionFeeRetrieve.Response getDefaultDeliveryFee() {
        return DeliveryOptionFeeRetrieve.Response.fromDto(
                deliveryOptionRetrieveService.getDefaultDeliveryFee()
        );
    }

}
