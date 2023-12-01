package com.liberty52.auth.notice.service;

import com.liberty52.auth.global.utils.AdminRoleUtils;
import com.liberty52.auth.notice.repository.NoticeRepository;
import com.liberty52.auth.notice.web.dto.NoticeModifyRequestDto;
import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeModifyServiceImpl implements NoticeModifyService {
    private final NoticeRepository noticeRepository;

    @Override
    public void modifyNoticeByAdmin(String role, String noticeId, NoticeModifyRequestDto dto) {
        AdminRoleUtils.isAdmin(role);
        noticeRepository.findById(noticeId)
                .orElseThrow(() -> new ResourceNotFoundException("notice", "id", noticeId))
                .modify(dto.getTitle(), dto.getContent(), dto.isCommentable());
    }
}
