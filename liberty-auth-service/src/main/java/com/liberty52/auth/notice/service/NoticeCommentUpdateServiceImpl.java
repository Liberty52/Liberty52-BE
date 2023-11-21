package com.liberty52.auth.notice.service;

import com.liberty52.auth.global.exception.external.forbidden.NotYourNoticeCommentException;
import com.liberty52.auth.global.exception.external.notfound.NoticeCommentNotFoundById;
import com.liberty52.auth.global.exception.external.notfound.NoticeNotFoundById;
import com.liberty52.auth.global.exception.external.unauthorized.AuthNotFoundException;
import com.liberty52.auth.notice.entity.Notice;
import com.liberty52.auth.notice.entity.NoticeComment;
import com.liberty52.auth.notice.repository.NoticeCommentRepository;
import com.liberty52.auth.notice.repository.NoticeRepository;
import com.liberty52.auth.notice.web.dto.NoticeCommentRequestDto;
import com.liberty52.auth.user.entity.Auth;
import com.liberty52.auth.user.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeCommentUpdateServiceImpl implements NoticeCommentUpdateService {
    private final AuthRepository authRepository;
    private final NoticeRepository noticeRepository;
    private final NoticeCommentRepository noticeCommentRepository;
    @Override
    public NoticeComment updateNoticeComment(String userId, String noticeId, String commentId, NoticeCommentRequestDto requestDto) {
        Auth auth = authRepository.findById(userId).orElseThrow(AuthNotFoundException::new);
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(()-> new NoticeNotFoundById(noticeId));
        NoticeComment noticeComment = noticeCommentRepository.findById(commentId).orElseThrow(()-> new NoticeCommentNotFoundById(commentId));
        if(!(noticeComment.getWriter().getId().equals(userId))){
            throw new NotYourNoticeCommentException(userId);
        }
        noticeComment.modifyContent(requestDto.getContent());
        return noticeCommentRepository.save(noticeComment);
    }
}
