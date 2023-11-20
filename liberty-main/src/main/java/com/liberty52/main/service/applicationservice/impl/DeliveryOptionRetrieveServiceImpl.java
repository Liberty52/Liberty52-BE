package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.service.applicationservice.DeliveryOptionRetrieveService;
import com.liberty52.main.service.controller.dto.DeliveryOptionDto;
import com.liberty52.main.service.entity.DeliveryOption;
import com.liberty52.main.service.repository.DeliveryOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryOptionRetrieveServiceImpl implements DeliveryOptionRetrieveService {

    private final DeliveryOptionRepository deliveryOptionRepository;

    @Override
    @Transactional
    public DeliveryOptionDto getDefaultDeliveryFee() {
        DeliveryOption deliveryOption = deliveryOptionRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("DELIVERY_OPTION", "id", "1"));

        return DeliveryOptionDto.fromEntity(deliveryOption);
    }
}
