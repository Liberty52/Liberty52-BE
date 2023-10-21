package com.liberty52.smartcourier.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourierCompanyListDto {

    @JsonProperty("Company")
    private List<CompanyResponse> companies;

    public record CompanyResponse(
            @JsonProperty("International")
            Boolean international,
            @JsonProperty("Code")
            String code,
            @JsonProperty("Name")
            String name
    ) {}
}
