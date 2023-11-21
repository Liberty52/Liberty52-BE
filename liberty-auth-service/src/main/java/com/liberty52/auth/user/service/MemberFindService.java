package com.liberty52.auth.user.service;

import com.liberty52.auth.user.web.dto.FindRequestDto;

import java.util.List;

public interface MemberFindService {
   List<String> findEmailByNameAndPhoneNumber(FindRequestDto dto);
}
