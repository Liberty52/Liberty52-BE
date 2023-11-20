package com.liberty52.auth.service.controller.dto;

import com.liberty52.auth.service.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.liberty52.auth.global.utils.PagingUtils.*;

@Data
public class NoticeRetrieveResponse {

    public static final NoticeRetrieveResponse EMPTY_RESOURCE_RESPONSE = new NoticeRetrieveResponse();

    private List<NoticeContent> contents;
    private long startPage;
    private long currentPage;
    private long lastPage;
    private long totalPage;
    private long totalCount;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NoticeContent{
        private String noticeId;
        private String title;
        private String createdAt;

        public NoticeContent(Notice notice) {
            this.noticeId = notice.getId();
            this.title = notice.getTitle();
            this.createdAt = notice.getCreatedAt().toLocalDate().toString();
        }
    }

    private NoticeRetrieveResponse(){
        contents = new ArrayList<>();
    }

    public NoticeRetrieveResponse(List<Notice> notices, Map<String, Long> pageNum) {
        contents = notices.stream().map(NoticeContent::new)
                .toList();
        startPage = pageNum.get(START_PAGE);
        currentPage = pageNum.get(CURRENT_PAGE);
        lastPage = pageNum.get(LAST_PAGE);
        totalPage = pageNum.get(TOTAL_PAGE);
    }

    public static NoticeRetrieveResponse of(List<Notice> notices, PageInfo pageInfo) {
        NoticeRetrieveResponse response = new NoticeRetrieveResponse();
        response.contents = notices.stream()
                .map(NoticeContent::new)
                .toList();
        response.startPage = pageInfo.getStartPage();;
        response.currentPage = pageInfo.getCurrentPage();
        response.lastPage = pageInfo.getLastPage();
        response.totalPage = pageInfo.getTotalPage();
        response.totalCount = pageInfo.getTotalCount();
        return response;
    }
}
