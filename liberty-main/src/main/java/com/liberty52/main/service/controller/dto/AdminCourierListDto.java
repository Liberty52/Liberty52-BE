package com.liberty52.main.service.controller.dto;

import lombok.Builder;

import java.util.List;

public class AdminCourierListDto {

    @Builder
    public record Response(
            MetaResponse meta,
            List<DocumentResponse> documents
    ) {
    }

    @Builder
    public record MetaResponse(
            Boolean international,
            Integer totalCount
    ) {
    }

    @Builder
    public record DocumentResponse(
            String courierCode,
            String courierName
    ) {
    }
}
