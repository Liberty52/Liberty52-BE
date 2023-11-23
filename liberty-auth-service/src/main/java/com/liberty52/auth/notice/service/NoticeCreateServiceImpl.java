package com.liberty52.auth.notice.service;

import com.liberty52.auth.global.utils.AdminRoleUtils;
import com.liberty52.auth.notice.entity.Notice;
import com.liberty52.auth.notice.repository.NoticeRepository;
import com.liberty52.auth.notice.web.dto.NoticeCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeCreateServiceImpl implements NoticeCreateService {

    private final NoticeRepository noticeRepository;

    @Override
    public void createNoticeByAdmin(String role, NoticeCreateRequestDto dto) {
        AdminRoleUtils.checkRole(role);
        Notice notice = Notice.create(dto.getTitle(), dto.getContent(), dto.isCommentable());
        noticeRepository.save(notice);
    }
}
