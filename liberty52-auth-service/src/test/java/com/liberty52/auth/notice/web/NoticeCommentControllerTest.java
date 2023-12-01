package com.liberty52.auth.notice.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.liberty52.auth.notice.entity.Notice;
import com.liberty52.auth.notice.entity.NoticeComment;
import com.liberty52.auth.notice.service.NoticeCommentCreateService;
import com.liberty52.auth.notice.service.NoticeCommentDeleteService;
import com.liberty52.auth.notice.service.NoticeCommentRetrieveService;
import com.liberty52.auth.notice.service.NoticeCommentUpdateService;
import com.liberty52.auth.notice.web.dto.NoticeCommentRequestDto;
import com.liberty52.auth.notice.web.rest.NoticeCommentController;
import com.liberty52.auth.user.entity.Auth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoticeCommentController.class)
public class NoticeCommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private NoticeCommentCreateService noticeCommentCreateService;
    @MockBean
    private NoticeCommentRetrieveService noticeCommentRetrieveService;
    @MockBean
    private NoticeCommentUpdateService noticeCommentUpdateService;
    @MockBean
    private NoticeCommentDeleteService noticeCommentDeleteService;

    private final String testNoticeId = "NOTICE-001";
    private final String testWriterID = "TESTER-001";

    @Test
    @WithMockUser
    void 공지사항댓글생성_성공() throws Exception {
        //Given
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("content","Test Comment Content");
        String jsonData = objectMapper.writeValueAsString(requestData);

        NoticeComment noticeCommentMock = mock(NoticeComment.class);
        Notice noticeMock = mock(Notice.class);
        Auth writerMock = mock(Auth.class);
        when(noticeCommentCreateService.createNoticeComment(anyString(), anyString(), any(NoticeCommentRequestDto.class)))
                .thenReturn(noticeCommentMock);
        when(noticeCommentMock.getNotice()).thenReturn(noticeMock);
        when(noticeCommentMock.getWriter()).thenReturn(writerMock);

        //When
        mockMvc.perform(post("/notices/" + testNoticeId + "/comments")
                        .header(HttpHeaders.AUTHORIZATION, testWriterID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData)
                        .with(csrf()))
                //Then
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void 공지사항댓글생성_실패_내용없음() throws Exception {
        //given
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("content"," ");
        String jsonData = objectMapper.writeValueAsString(requestData);

        //when
        mockMvc.perform(post("/notices/" + testNoticeId + "/comments")
                        .header(HttpHeaders.AUTHORIZATION, testWriterID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData)
                        .with(csrf()))
                //then
                .andExpect(status().isBadRequest());
    }
}
