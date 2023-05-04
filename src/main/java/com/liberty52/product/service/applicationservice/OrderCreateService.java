package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.controller.dto.*;
import org.springframework.web.multipart.MultipartFile;

public interface OrderCreateService {

    @Deprecated
    MonoItemOrderResponseDto save(String authId, MultipartFile imageFile, MonoItemOrderRequestDto dto);

    PaymentConfirmResponseDto confirmFinalApprovalOfCardPayment(String authId, String orderId);

    PaymentCardResponseDto createCardPaymentOrders(String authId, OrderCreateRequestDto dto, MultipartFile imageFile);

    PaymentCardResponseDto createCardPaymentOrdersByCarts(String authId, OrderCreateRequestDto dto);

    PaymentVBankResponseDto createVBankPaymentOrders(String authId, OrderCreateRequestDto dto, MultipartFile imageFile);

    PaymentVBankResponseDto createVBankPaymentOrdersByCarts(String authId, OrderCreateRequestDto dto);

}
