package com.liberty52.auth.notice.repository;

import com.liberty52.auth.notice.entity.NoticeComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeCommentRepository extends JpaRepository<NoticeComment, String> {
    Page<NoticeComment> findAllByNoticeId(String noticeId, Pageable pageable);
}
