package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.service.controller.dto.FindRequestDto;

import java.util.List;

public interface MemberFindService {
   List<String> findEmailByNameAndPhoneNumber(FindRequestDto dto);
}
