package com.liberty52.auth.user.service;

import com.liberty52.auth.user.web.dto.ModifyRequestDto;
import com.liberty52.auth.user.web.dto.ModifyResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface MemberModifyService {

   ModifyResponseDto getMemberInfo(String userId);

   void modifyMemberInfo(String userId, ModifyRequestDto dto, MultipartFile imageFile);
}
