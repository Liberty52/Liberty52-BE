package com.liberty52.auth.user.web.rest;

import com.liberty52.auth.user.service.MemberFindService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberFindController.class)
class MemberFindControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberFindService memberFindService;

    @Test
    @WithMockUser
    @DisplayName("WRITER_ID로 사용자 정보 조회 성공")
    void WRITER_ID로_사용자_정보_조회_성공() throws Exception {
        // Given
        Map<String, String> expectedUserInfos = new HashMap<>();
        expectedUserInfos.put("writer1", "writer1@example.com");
        expectedUserInfos.put("writer2", "writer2@example.com");

        when(memberFindService.getUserInfosByWriterIds(anyList())).thenReturn(expectedUserInfos);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/user-infos/writer-ids")
                        .param("writerIds", "writer1", "writer2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.writer1").value("writer1@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.writer2").value("writer2@example.com"))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()));
    }


    @Test
    @WithMockUser
    @DisplayName("WRITER_ID로 사용자 정보 조회 실패 - 내용 없음")
    void WRITER_ID로_사용자_정보_조회_실패_내용_없음() throws Exception {
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/user-infos/writer-ids"))
                .andExpect(status().isBadRequest());
    }
}
