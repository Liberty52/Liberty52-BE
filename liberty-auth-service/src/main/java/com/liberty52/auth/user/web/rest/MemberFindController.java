package com.liberty52.auth.user.web.rest;

import com.liberty52.auth.user.service.MemberFindService;
import com.liberty52.auth.user.web.dto.FindRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberFindController {
  private final MemberFindService memberFindService;

  @PostMapping("/find-email")
  public ResponseEntity<List<String>> findEmailByNameAndPhoneNumber(@Validated @RequestBody FindRequestDto dto){
    List<String> emailList = memberFindService.findEmailByNameAndPhoneNumber(dto);
    return ResponseEntity.ok().body(emailList);
  }
}
