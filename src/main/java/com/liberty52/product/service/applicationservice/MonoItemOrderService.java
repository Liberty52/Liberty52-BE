package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.controller.dto.MonoItemOrderRequestDto;
import com.liberty52.product.service.controller.dto.MonoItemOrderResponseDto;
import com.liberty52.product.service.controller.dto.PreregisterOrderRequestDto;
import com.liberty52.product.service.controller.dto.PreregisterOrderResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface MonoItemOrderService {

    @Deprecated
    MonoItemOrderResponseDto save(String authId, MultipartFile imageFile, MonoItemOrderRequestDto dto);

    PreregisterOrderResponseDto preregisterCardPaymentOrders(String authId, PreregisterOrderRequestDto dto, MultipartFile imageFile);
}
