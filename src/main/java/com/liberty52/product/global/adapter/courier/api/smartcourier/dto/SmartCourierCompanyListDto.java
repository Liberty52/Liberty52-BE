package com.liberty52.product.global.adapter.courier.api.smartcourier.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmartCourierCompanyListDto {

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

    public Map<String, List<Map<String, Object>>> asMap() {
        if (this.companies == null) return null;
        var value = new ArrayList<Map<String, Object>>();
        for (CompanyResponse company : this.companies) {
            var map = new HashMap<String, Object>();
            map.put("international", company.international);
            map.put("code", company.code);
            map.put("name", company.name);
            value.add(map);
        }
        var map = new HashMap<String, List<Map<String, Object>>>();
        map.put("companies", value);
        return map;
    }

    public static SmartCourierCompanyListDto asDto(Map<String, List<Map<String, Object>>> map) {
        if (map == null || map.isEmpty()) return null;
        var companies = map.get("companies").stream()
                .map(it -> {
                    Boolean internation = (Boolean) it.get("international");
                    String code = (String) it.get("code");
                    String name = (String) it.get("name");
                    return new CompanyResponse(internation, code, name);
                })
                .toList();
        return new SmartCourierCompanyListDto(companies);
    }
}
