package com.liberty52.auth.service.applicationservice.impl;

import com.liberty52.auth.global.exception.external.notfound.NoticeNotFoundById;
import com.liberty52.auth.service.applicationservice.NoticeCommentRetrieveService;
import com.liberty52.auth.service.entity.Notice;
import com.liberty52.auth.service.entity.NoticeComment;
import com.liberty52.auth.service.repository.NoticeCommentRepository;
import com.liberty52.auth.service.repository.NoticeRepository;
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
