package com.liberty52.auth.service.applicationservice.impl;

import com.liberty52.auth.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.auth.global.utils.AdminRoleUtils;
import com.liberty52.auth.service.applicationservice.NoticeModifyService;
import com.liberty52.auth.service.controller.dto.NoticeModifyRequestDto;
import com.liberty52.auth.service.repository.NoticeRepository;
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
        AdminRoleUtils.checkRole(role);
        noticeRepository.findById(noticeId)
                .orElseThrow(() -> new ResourceNotFoundException("notice", "id", noticeId))
                .modify(dto.getTitle(), dto.getContent(), dto.isCommentable());
    }
}
