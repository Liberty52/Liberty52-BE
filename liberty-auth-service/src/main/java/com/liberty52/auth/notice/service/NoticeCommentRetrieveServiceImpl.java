package com.liberty52.auth.notice.service;

import com.liberty52.auth.global.exception.external.notfound.NoticeNotFoundById;
import com.liberty52.auth.notice.entity.Notice;
import com.liberty52.auth.notice.entity.NoticeComment;
import com.liberty52.auth.notice.repository.NoticeCommentRepository;
import com.liberty52.auth.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeCommentRetrieveServiceImpl implements NoticeCommentRetrieveService {
    private final NoticeRepository noticeRepository;
    private final NoticeCommentRepository noticeCommentRepository;
    @Override
    public Page<NoticeComment> retrieveNoticeComment(String noticeId, Pageable pageable) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(()-> new NoticeNotFoundById(noticeId));
        return noticeCommentRepository.findAllByNoticeId(noticeId, pageable);
    }
}
