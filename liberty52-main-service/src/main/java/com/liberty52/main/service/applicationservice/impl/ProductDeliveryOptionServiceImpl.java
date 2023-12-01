package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.common.exception.external.badrequest.BadRequestException;
import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.global.util.Validator;
import com.liberty52.main.service.applicationservice.ProductDeliveryOptionService;
import com.liberty52.main.service.controller.dto.AdminProductDeliveryOptionsDto;
import com.liberty52.main.service.entity.ProductDeliveryOption;
import com.liberty52.main.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductDeliveryOptionServiceImpl implements ProductDeliveryOptionService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public AdminProductDeliveryOptionsDto.Response create(
            String role,
            String productId,
            AdminProductDeliveryOptionsDto.Request dto
    ) {
        Validator.isAdmin(role);

        validateDto(dto);

        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product", "id", productId));
        if (product.getDeliveryOption() != null) {
            throw new BadRequestException("이미 등록되어 있는 배송옵션이 존재합니다");
        }

        var deliveryOption = ProductDeliveryOption.of(dto.courierName(), dto.fee(), product);

        return AdminProductDeliveryOptionsDto.Response.from(deliveryOption);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminProductDeliveryOptionsDto.Response getByProductIdForAdmin(
            String role,
            String productId
    ) {
        Validator.isAdmin(role);

        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product", "id", productId));

        return product.getDeliveryOption() != null ?
                AdminProductDeliveryOptionsDto.Response.from(product.getDeliveryOption()) :
                AdminProductDeliveryOptionsDto.Response.empty(productId);
    }

    @Override
    @Transactional
    public AdminProductDeliveryOptionsDto.Response update(
            String role,
            String productId,
            AdminProductDeliveryOptionsDto.Request dto
    ) {
        Validator.isAdmin(role);

        validateDto(dto);

        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product", "id", productId));

        var deliveryOption = product.getDeliveryOption();
        if (deliveryOption == null) {
            throw new ResourceNotFoundException("deliveryOption");
        }

        deliveryOption.update(dto.courierName(), dto.fee());

        return AdminProductDeliveryOptionsDto.Response.from(deliveryOption);
    }

    private void validateDto(AdminProductDeliveryOptionsDto.Request dto) {
        if (Validator.areNullOrBlank(dto.courierName())
                || dto.fee() == null || dto.fee() < 0) {
            throw new BadRequestException("모든 파라미터를 올바르게 요청해주세요");
        }
    }
}
