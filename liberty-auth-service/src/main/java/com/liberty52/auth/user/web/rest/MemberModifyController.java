package com.liberty52.auth.user.web.rest;

import com.liberty52.auth.user.service.MemberModifyService;
import com.liberty52.auth.user.web.dto.ModifyRequestDto;
import com.liberty52.auth.user.web.dto.ModifyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class MemberModifyController {

  private final MemberModifyService memberModifyService;

  @GetMapping("/my")
  @ResponseStatus(HttpStatus.OK)
  public ModifyResponseDto getMemberInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String userId){
    return memberModifyService.getMemberInfo(userId);
  }

  @PutMapping("/my")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void modifyMemberInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String userId,
      @Validated @RequestPart("dto") ModifyRequestDto dto,
      @RequestPart(value = "file", required = false) MultipartFile imageFile){
    memberModifyService.modifyMemberInfo(userId,dto,imageFile);
  }

}
