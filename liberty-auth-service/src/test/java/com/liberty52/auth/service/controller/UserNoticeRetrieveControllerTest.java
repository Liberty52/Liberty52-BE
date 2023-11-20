package com.liberty52.auth.service.controller;

import com.liberty52.auth.global.config.WebSecurityConfig;
import com.liberty52.auth.global.exception.external.ErrorResponse;
import com.liberty52.auth.global.exception.external.RestExceptionHandler;
import com.liberty52.auth.global.exception.external.badrequest.PageNumberOutOfRangeException;
import com.liberty52.auth.global.exception.external.notfound.NoticeNotFoundById;
import com.liberty52.auth.service.applicationservice.NoticeRetrieveService;
import com.liberty52.auth.service.controller.dto.NoticeDetailResponse;
import com.liberty52.auth.service.controller.dto.NoticeRetrieveResponse;
import com.liberty52.auth.service.entity.Notice;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {NoticeRetrieveController.class, RestExceptionHandler.class},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class)},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class,
                OAuth2ClientAutoConfiguration.class,
                OAuth2ResourceServerAutoConfiguration.class})
class UserNoticeRetrieveControllerTest {

    @InjectMocks
    NoticeRetrieveController userNoticeRetrieveController;

    @MockBean
    RestExceptionHandler restExceptionHandler;

    @MockBean
    NoticeRetrieveService userNoticeRetrieveService;


    @Autowired
    MockMvc mockMvc;

    final String LIST_RETRIEVE_API = "/notices?size=%d&page=%d";
    final String DETAIL_RETRIEVE_API = "/notices/%s";
    @Test
    void retrieveUserNotice  () throws Exception{

        Notice notice = createMockNotice();
        Map<String,Long> pageNum = new HashMap<>();
        pageNum.put("startPage",1L);
        pageNum.put("currentPage",1L);
        pageNum.put("lastPage",1L);
        pageNum.put("totalPage",1L);
        NoticeRetrieveResponse response = new NoticeRetrieveResponse(List.of(notice), pageNum);

        //given

        given(userNoticeRetrieveService.retrieveUserNotice(any()))
                .willReturn(response);

        String api = String.format(LIST_RETRIEVE_API,10,0);
        mockMvc.perform(get(api))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startPage").value(1))
                .andExpect(jsonPath("$.currentPage").value(1))
                .andExpect(jsonPath("$.lastPage").value(1))
                .andExpect(jsonPath("$.totalPage").value(1))
                .andExpect(jsonPath("$.contents[0].noticeId").value(notice.getId()))
                .andExpect(jsonPath("$.contents[0].title").value(notice.getTitle()))
                .andExpect(jsonPath("$.contents[0].createdAt").value(notice.getCreatedAt().toLocalDate().toString()));
    }

    @Test
    void retrieveUserNotice_PageNumberOutOfRangeException () throws Exception{
        //given
        final String api = String.format(LIST_RETRIEVE_API,10,0);
        PageNumberOutOfRangeException exception = new PageNumberOutOfRangeException();
        ErrorResponse response = ErrorResponse.createErrorResponse(
                exception, api);

        given(userNoticeRetrieveService.retrieveUserNotice(any()))
                .willThrow(PageNumberOutOfRangeException.class);
        given(restExceptionHandler.handleGlobalException(any(),any()))
                .willReturn(ResponseEntity.status(exception.getHttpStatus())
                        .body(response));
        //when
        mockMvc.perform(get(api))
        //then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(response.getErrorCode()))
                .andExpect(jsonPath("$.errorName").value(response.getErrorName()))
                .andExpect(jsonPath("$.errorMessage").value(response.getErrorMessage()))
                .andExpect(jsonPath("$.path").value(response.getPath()));
    }

    @Test
    void retrieveDetailNotice () throws Exception{
        //given

        Notice mockNotice = createMockNotice();
        NoticeDetailResponse response = new NoticeDetailResponse(mockNotice);
        given(userNoticeRetrieveService.retrieveUserNoticeDetail(anyString()))
                .willReturn(response);
        String api = String.format(DETAIL_RETRIEVE_API,mockNotice.getId());
        //when
        mockMvc.perform(get(api))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.noticeId").value(mockNotice.getId()))
                .andExpect(jsonPath("$.title").value(mockNotice.getTitle()))
                .andExpect(jsonPath("$.content").value(mockNotice.getContent()))
                .andExpect(jsonPath("$.createdAt").value(mockNotice.getCreatedAt().toLocalDate().toString()))
                .andExpect(jsonPath("$.commentable").value(false));

        //then
    }

    @Test
    void retrieveDetailNotice_NoticeNotFoundById () throws Exception{
        //given
        String noticeId = "BAR";
        String api = String.format(DETAIL_RETRIEVE_API, noticeId);
        NoticeNotFoundById exception = new NoticeNotFoundById(noticeId);
        ErrorResponse response = ErrorResponse.createErrorResponse(
                exception, api);

        given(userNoticeRetrieveService.retrieveUserNoticeDetail(any()))
                .willThrow(exception);
        given(restExceptionHandler.handleGlobalException(any(),any()))
                .willReturn(ResponseEntity.status(exception.getHttpStatus())
                        .body(response));

        //when
        mockMvc.perform(get(api))
                //then
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(response.getErrorCode()))
                .andExpect(jsonPath("$.errorName").value(response.getErrorName()))
                .andExpect(jsonPath("$.errorMessage").value(response.getErrorMessage()))
                .andExpect(jsonPath("$.path").value(response.getPath()));


    }

    private Notice createMockNotice() {
        Notice notice = Notice.create("FOO", "BAR", false);
        return notice;
    }

}
