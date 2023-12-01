package com.liberty52.main.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liberty52.common.exception.external.ErrorResponse;
import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.global.exception.external.RestExceptionHandler;
import com.liberty52.main.global.exception.external.forbidden.InvalidRoleException;
import com.liberty52.main.service.applicationservice.ReviewReplyCreateService;
import com.liberty52.main.service.applicationservice.ReviewReplyModifyService;
import com.liberty52.main.service.controller.dto.ReplyCreateRequestDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static com.liberty52.main.global.constants.RoleConstants.ADMIN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.doThrow;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {ReviewReplyAdminController.class, RestExceptionHandler.class})
class ReviewReplyAdminControllerTest {

    final String mockReviewId = "foo";
    final String createReplyUrl = "/admin/reviews/%s/replies";
    final String mockAdminId = "bar";
    final String mockAdminRole = ADMIN;
    final String mockUserRole = "USER";
    final String mockReplyContent = "Hello world";
    final String headerRole = "LB-Role";
    @InjectMocks
    ReviewReplyAdminController controller;
    @MockBean
    ReviewReplyCreateService reviewReplyCreateService;
    @MockBean
    ReviewReplyModifyService reviewReplyModifyService;
    @MockBean
    RestExceptionHandler exceptionHandler;
    @Autowired
    MockMvc mockMvc;

    @Test
    void Craete_Reply_Success_Status_CRAETED() throws Exception {
        //given
        ReplyCreateRequestDto dto = ReplyCreateRequestDto.createForTest(mockReplyContent);
        //when
        mockMvc.perform(post(String.format(createReplyUrl, mockReviewId))
                        .header(HttpHeaders.AUTHORIZATION, mockAdminId)
                        .header(headerRole, mockAdminRole)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                //then
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void CREATE_REPLY_FAIL_STATUS_FORBIDDEN_InvalidRoleException() throws Exception {
        //given
        InvalidRoleException exception = new InvalidRoleException(mockUserRole);
        doThrow(InvalidRoleException.class)
                .when(reviewReplyCreateService)
                .createReviewReplyByAdmin(any(), any(), any(), any());

        given(exceptionHandler.handleGlobalException(any(), any()))
                .willReturn(
                        ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(ErrorResponse.createErrorResponse(exception, String.format(createReplyUrl, mockReviewId)))
                );

        ReplyCreateRequestDto dto = ReplyCreateRequestDto.createForTest(mockReplyContent);
        //when
        mockMvc.perform(post(String.format(createReplyUrl, mockReviewId))
                        .header(HttpHeaders.AUTHORIZATION, mockAdminId)
                        .header(headerRole, mockUserRole)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                //then
                .andExpect(status().is(exception.getHttpStatus().value()))
                .andExpect(jsonPath("$.httpStatus").value(exception.getHttpStatus().getReasonPhrase().toUpperCase()))
                .andExpect(jsonPath("$.errorCode").value(exception.getErrorCode()))
                .andExpect(jsonPath("$.errorMessage").value(exception.getErrorMessage()))
                .andExpect(jsonPath("$.errorName").value(exception.getErrorName()))
                .andExpect(jsonPath("$.path").value(String.format(createReplyUrl, mockReviewId)))
                .andDo(print());

    }

    @Test
    void CREATE_REPLY_FAIL_STATUS_NOT_FOUND_ResourceNotFoundException() throws Exception {
        //given
        ResourceNotFoundException exception = new ResourceNotFoundException("Review", "ID", mockReviewId);
        doThrow(ResourceNotFoundException.class)
                .when(reviewReplyCreateService)
                .createReviewReplyByAdmin(any(), any(), any(), any());

        given(exceptionHandler.handleGlobalException(any(), any()))
                .willReturn(
                        ResponseEntity.status(exception.getHttpStatus())
                                .body(ErrorResponse.createErrorResponse(exception, String.format(createReplyUrl, mockReviewId)))
                );

        ReplyCreateRequestDto dto = ReplyCreateRequestDto.createForTest(mockReplyContent);
        //when
        mockMvc.perform(post(String.format(createReplyUrl, mockReviewId))
                        .header(HttpHeaders.AUTHORIZATION, mockAdminId)
                        .header(headerRole, mockAdminRole)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                //then
                .andExpect(status().is(exception.getHttpStatus().value()))
                .andExpect(jsonPath("$.httpStatus").value(
                        exception.getHttpStatus().getReasonPhrase().toUpperCase().replace(" ", "_")))
                .andExpect(jsonPath("$.errorCode").value(exception.getErrorCode()))
                .andExpect(jsonPath("$.errorMessage").value(exception.getErrorMessage()))
                .andExpect(jsonPath("$.errorName").value(exception.getErrorName()))
                .andExpect(jsonPath("$.path").value(String.format(createReplyUrl, mockReviewId)))
                .andDo(print());

    }

}
