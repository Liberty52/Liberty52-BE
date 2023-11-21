package com.liberty52.auth.service.controller;

import com.liberty52.auth.global.config.WebSecurityConfig;
import com.liberty52.auth.global.exception.external.RestExceptionHandler;
import com.liberty52.auth.user.entity.Role;
import com.liberty52.auth.user.service.CustomerInfoRetrieveService;
import com.liberty52.auth.user.web.dto.CustomerInfoListResponseDto;
import com.liberty52.auth.user.web.dto.CustomerInfoResponseDto;
import com.liberty52.auth.user.web.rest.CustomerInfoRetrieveController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {CustomerInfoRetrieveController.class, RestExceptionHandler.class},
            excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class)},
            excludeAutoConfiguration = {SecurityAutoConfiguration.class,
            SecurityFilterAutoConfiguration.class,
            OAuth2ClientAutoConfiguration.class,
            OAuth2ResourceServerAutoConfiguration.class})
class CustomerInfoRetrieveControllerTest {
    @InjectMocks
    CustomerInfoRetrieveController controller;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    RestExceptionHandler restExceptionHandler;
    @MockBean
    CustomerInfoRetrieveService service;
    final String LIST_RETRIEVE_API = "/admin/customer-info?page=%d&size=%d";

    @Test
    void userInfoListByAdmin() throws Exception {
        int page = 0;
        int size = 3;
        int totalCount = 10;
        List<CustomerInfoResponseDto> list = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (int i = 0; i < totalCount; i++) {
            String role = i % 2 == 0 ? Role.ADMIN.name() : Role.USER.name();
            list.add(CustomerInfoResponseDto.of(
                    "id" + i,
                    "name" + i,
                    "phoneNumber" + i,
                    now,
                    role
            ));
        }

        while(true) {
            CustomerInfoListResponseDto dto = nextGiven(list, page, size);
            if (dto.getNumberOfElements()==0) break;
            String api = String.format(LIST_RETRIEVE_API,page++,size);
            ResultActions resultActions = mockMvc.perform(get(api).header("LB-Role", Role.ADMIN.name()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.infoList.length()").value(dto.getInfoList().size()))
                    .andExpect(jsonPath("$.totalCount").value(dto.getTotalCount()))
                    .andExpect(jsonPath("$.numberOfElements").value(dto.getNumberOfElements()))
                    .andExpect(jsonPath("$.pageNumber").value(dto.getPageNumber()))
                    .andExpect(jsonPath("$.hasPrev").value(dto.isHasPrev()))
                    .andExpect(jsonPath("$.hasNext").value(dto.isHasNext()))
                    .andExpect(jsonPath("$.first").value(dto.isFirst()))
                    .andExpect(jsonPath("$.last").value(dto.isLast()));
            for (int i = 0; i < dto.getInfoList().size(); i++) {
                String item = String.format("$.infoList[%d]", i);
                resultActions.andExpect(jsonPath(item + ".id").value(dto.getInfoList().get(i).getId()))
                        .andExpect(jsonPath(item + ".name").value(dto.getInfoList().get(i).getName()))
                        .andExpect(jsonPath(item + ".phoneNumber").value(dto.getInfoList().get(i).getPhoneNumber()))
                        .andExpect(jsonPath(item + ".createdAt").value(dto.getInfoList().get(i).getCreatedAt().toString()));
            }
        }
    }

    CustomerInfoListResponseDto nextGiven(List<CustomerInfoResponseDto> elements, int page, int size) {
        CustomerInfoListResponseDto dto = CustomerInfoListResponseDto.of(
                elements.stream()
                        .skip((long) page * size)
                        .limit(size)
                        .toList(),
                elements.size(),
                page,
                size);
        given(service.retrieveCustomerInfoByAdmin(any(), any()))
                .willReturn(dto);
        return dto;
    }
}