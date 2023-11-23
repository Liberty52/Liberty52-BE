package com.liberty52.auth.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liberty52.auth.global.config.WebSecurityConfig;
import com.liberty52.auth.user.service.ProfileRetrieveService;
import com.liberty52.auth.user.web.dto.ReviewerProfileResponse;
import com.liberty52.auth.user.web.rest.ProfileRetrieveController;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = {ProfileRetrieveController.class},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class)},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class,
                OAuth2ClientAutoConfiguration.class,
                OAuth2ResourceServerAutoConfiguration.class}
)
class ProfileRetrieveControllerTest {

    @Autowired
    MockMvc mvc;

    @InjectMocks
    ProfileRetrieveController controller;

    @MockBean
    ProfileRetrieveService service;

    final String RETRIEVE_REVIEWER_URL = "/info";
    @Test
    void retrieveReviewerProfile () throws Exception{
        //given
        Set<String> ids =  Set.of("1","2","3");
        Map<String, ReviewerProfileResponse> mockResponse= new HashMap<>();

        for (String id : ids)
            mockResponse.put(id,new ReviewerProfileResponse("authorName"+id, "authorProfileUrl"+id));

        String body = new ObjectMapper().writeValueAsString(ids);

        given(service.retrieveReviewerProfile(any()))
                .willReturn(mockResponse);
        //when //then
        ResultActions actions = mvc.perform(post(RETRIEVE_REVIEWER_URL)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        for (int i = 1; i <= 3; i++) {
                actions.andExpect(jsonPath("$."+i+".authorName").value("authorName"+i))
                        .andExpect(jsonPath("$."+i+".authorProfileUrl").value("authorProfileUrl"+i));
        }
                actions.andDo(print());
    }

}
