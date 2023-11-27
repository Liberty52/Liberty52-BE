package com.liberty52.auth.user.service;

import com.liberty52.auth.user.web.dto.FindRequestDto;

import java.util.List;
import java.util.Map;

public interface MemberFindService {
    List<String> findEmailByNameAndPhoneNumber(FindRequestDto dto);

    Map<String, String> getUserInfosByWriterIds(List<String> writerIds);
}
