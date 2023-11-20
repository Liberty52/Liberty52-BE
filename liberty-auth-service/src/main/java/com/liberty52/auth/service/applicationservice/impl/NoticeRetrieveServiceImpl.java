package com.liberty52.auth.service.applicationservice.impl;

import com.liberty52.auth.global.exception.external.notfound.NoticeNotFoundById;
import com.liberty52.auth.global.utils.AdminRoleUtils;
import com.liberty52.auth.global.utils.PagingUtils;
import com.liberty52.auth.service.applicationservice.NoticeRetrieveService;
import com.liberty52.auth.service.controller.dto.NoticeDetailResponse;
import com.liberty52.auth.service.controller.dto.NoticeRetrieveResponse;
import com.liberty52.auth.service.entity.Notice;
import com.liberty52.auth.service.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class NoticeRetrieveServiceImpl implements NoticeRetrieveService {

    private final NoticeRepository noticeRepository;

    @Override
    public NoticeRetrieveResponse retrieveUserNotice(Pageable pageable) {

        int pageNumber = pageable.getPageNumber();
        pageable = PageRequest.of(pageNumber, pageable.getPageSize(),
                Sort.by(PagingUtils.CREATED_AT).descending());

        Page<Notice> notices = noticeRepository.findAll(pageable);
        int totalPages = notices.getTotalPages();
        PagingUtils.validatePageNumber(pageNumber, totalPages);
        return createResponse(pageable, pageNumber, notices, totalPages);

    }

    @Override
    public NoticeDetailResponse retrieveUserNoticeDetail(String noticeId) {
        return new NoticeDetailResponse(noticeRepository.findById(noticeId)
                .orElseThrow(() ->
                        new NoticeNotFoundById(noticeId)));
    }

    private NoticeRetrieveResponse createResponse(Pageable pageable, int pageNumber,
                                                  Page<Notice> notices, int totalPages) {
        if (isEmptyPageNum(pageable, totalPages))
            return NoticeRetrieveResponse.EMPTY_RESOURCE_RESPONSE;

        return new NoticeRetrieveResponse(notices.getContent(),
                PagingUtils.getPageInfo(totalPages, pageNumber));
    }

    private boolean isEmptyPageNum(Pageable pageable, int totalPages) {
        return totalPages == 0 && pageable.getPageNumber() == 0;
    }

    @Override
    public NoticeRetrieveResponse retrieveNoticesByAdmin(String role, Pageable pageable) {
        AdminRoleUtils.checkRole(role);

        Page<Notice> notices = noticeRepository.findAllByOrderByCreatedAtDesc(pageable);
        if (CollectionUtils.isEmpty(notices.getContent())) {
            return NoticeRetrieveResponse.EMPTY_RESOURCE_RESPONSE;
        }
        PagingUtils.PageInfo pageInfo = PagingUtils.PageInfo.of(notices);

        return NoticeRetrieveResponse.of(notices.getContent(), pageInfo);
    }

    @Override
    public NoticeDetailResponse retrieveNoticeDetailByAdmin(String role, String noticeId) {
        AdminRoleUtils.checkRole(role);

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new NoticeNotFoundById(noticeId));

        return new NoticeDetailResponse(notice);
    }


}
