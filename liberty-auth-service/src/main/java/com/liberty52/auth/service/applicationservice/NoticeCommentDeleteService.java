package com.liberty52.auth.service.applicationservice;

public interface NoticeCommentDeleteService {
    void deleteNoticeComment(String userId, String noticeId, String commentId);

    void deleteNoticeCommentByAdmin(String role, String noticeId, String commentId);
}
