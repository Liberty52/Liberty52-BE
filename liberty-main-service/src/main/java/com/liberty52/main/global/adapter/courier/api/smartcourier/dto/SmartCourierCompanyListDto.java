package com.liberty52.main.global.adapter.courier.api.smartcourier.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmartCourierCompanyListDto {

    @JsonProperty("Company")
    private List<CompanyResponse> companies;

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

    public record CompanyResponse(
            @JsonProperty("International")
            Boolean international,
            @JsonProperty("Code")
            String code,
            @JsonProperty("Name")
            String name
    ) {
    }
}
