package com.liberty52.product.service.applicationservice.impl;

import com.liberty52.product.global.adapter.courier.CourierCompanyClient;
import com.liberty52.product.global.adapter.courier.api.smartcourier.dto.SmartCourierCompanyListDto;
import com.liberty52.product.global.exception.external.badrequest.BadRequestException;
import com.liberty52.product.global.exception.external.internalservererror.InternalServerErrorException;
import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.product.global.util.Validator;
import com.liberty52.product.service.applicationservice.OrderDeliveryService;
import com.liberty52.product.service.controller.dto.AdminAddOrderDeliveryDto;
import com.liberty52.product.service.controller.dto.AdminCourierListDto;
import com.liberty52.product.service.entity.OrderDelivery;
import com.liberty52.product.service.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderDeliveryServiceImpl implements OrderDeliveryService {

    @SuppressWarnings("rawtypes")
    private final CourierCompanyClient client;
    private final OrdersRepository ordersRepository;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public AdminCourierListDto.Response getCourierCompanyList(Boolean isInternational) {
        var courierCompanyList = SmartCourierCompanyListDto.asDto(client.getCourierCompanyList());

        if (courierCompanyList == null
                || courierCompanyList.getCompanies() == null
                || courierCompanyList.getCompanies().isEmpty()) {
            throw new InternalServerErrorException("택배사 리스트 조회가 실패하였습니다. 관리자에게 문의해주세요");
        }

        var documents = courierCompanyList.getCompanies().stream()
                .filter(it -> it.international() == isInternational)
                .map(it -> new AdminCourierListDto.DocumentResponse(it.code(), it.name()))
                .toList();

        var meta = AdminCourierListDto.MetaResponse.builder()
                .international(isInternational)
                .totalCount(documents.size())
                .build();

        return AdminCourierListDto.Response.builder()
                .meta(meta)
                .documents(documents)
                .build();
    }

    @Override
    @Transactional
    public AdminAddOrderDeliveryDto.Response add(String orderId, AdminAddOrderDeliveryDto.Request dto) {
        validateInputs(orderId, dto.courierCompanyCode(), dto.courierCompanyName(), dto.trackingNumber());

        validateCourierInfo(dto.courierCompanyCode(), dto.courierCompanyName());

        var order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("order", "id", orderId));

        var orderDelivery = OrderDelivery.builder()
                .courierCompanyCode(dto.courierCompanyCode())
                .courierCompanyName(dto.courierCompanyName())
                .trackingNumber(dto.trackingNumber())
                .order(order)
                .build();
        order.setOrderDelivery(orderDelivery);

        return AdminAddOrderDeliveryDto.Response.builder()
                .orderId(order.getId())
                .courierCompanyCode(orderDelivery.getCourierCompanyCode())
                .courierCompanyName(orderDelivery.getCourierCompanyName())
                .trackingNumber(orderDelivery.getTrackingNumber())
                .build();
    }

    private void validateInputs(String... inputs) {
        if (Validator.areNullOrBlank(inputs)) {
            throw new BadRequestException("모든 파라미터를 입력해주세요");
        }
    }

    private void validateCourierInfo(String code, String name) {
        var courierCompanyList = SmartCourierCompanyListDto.asDto(client.getCourierCompanyList());

        if (courierCompanyList == null
                || courierCompanyList.getCompanies() == null
                || courierCompanyList.getCompanies().isEmpty()) {
            throw new InternalServerErrorException("택배사 검증에 실패하였습니다. 관리자에게 문의해주세요");
        }

        var exist = courierCompanyList.getCompanies().stream()
                .anyMatch(it -> code.equals(it.code()) && name.equals(it.name()));
        if (!exist) {
            throw new BadRequestException("유효하지 않는 택배사입니다");
        }
    }
}
