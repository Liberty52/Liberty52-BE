package com.liberty52.auth.user.web.rest;

import com.liberty52.auth.user.service.MemberFindService;
import com.liberty52.auth.user.web.dto.FindRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberFindController {
  private final MemberFindService memberFindService;

  @PostMapping("/find-email")
  public ResponseEntity<List<String>> findEmailByNameAndPhoneNumber(@Validated @RequestBody FindRequestDto dto) {
    List<String> emailList = memberFindService.findEmailByNameAndPhoneNumber(dto);
    return ResponseEntity.ok().body(emailList);
  }


  @Operation(summary = "WRITER ID에 해당하는 사용자의 정보를 가져옴", description = "WRITER ID 목록에 있는 사용자의 ID 와 이메일 정보를 가져옴니다.")
  @GetMapping("/user-infos/writer-ids")
  public ResponseEntity<Map<String, String>> getUserInfosByWriterIds(@RequestParam List<String> writerIds) {
    Map<String, String> userinfos = memberFindService.getUserInfosByWriterIds(writerIds);
    return ResponseEntity.ok().body(userinfos);
  }


}
