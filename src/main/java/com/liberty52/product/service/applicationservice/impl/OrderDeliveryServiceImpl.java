package com.liberty52.product.service.applicationservice.impl;

import com.liberty52.product.global.exception.external.internalservererror.InternalServerErrorException;
import com.liberty52.product.service.applicationservice.OrderDeliveryService;
import com.liberty52.product.service.controller.dto.AdminCourierListDto;
import com.liberty52.smartcourier.api.CourierCompanyClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDeliveryServiceImpl implements OrderDeliveryService {

    private final CourierCompanyClient client;

    @Override
    public AdminCourierListDto.Response getCourierCompanyList(Boolean isInternational) {
        var courierCompanyList = client.getCourierCompanyList();

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
}
