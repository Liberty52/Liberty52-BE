package com.liberty52.auth.notice.service;

import com.liberty52.auth.global.utils.AdminRoleUtils;
import com.liberty52.auth.notice.entity.Notice;
import com.liberty52.auth.notice.repository.NoticeRepository;
import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeDeleteServiceImpl implements NoticeDeleteService {
  private final NoticeRepository noticeRepository;
  @Override
  public void deleteNoticeByAdmin(String role, String noticeId) {
    AdminRoleUtils.isAdmin(role);
    Notice notice = noticeRepository.findById(noticeId)
        .orElseThrow(() -> new ResourceNotFoundException("notice", "id", noticeId));
    noticeRepository.delete(notice);
  }
}
