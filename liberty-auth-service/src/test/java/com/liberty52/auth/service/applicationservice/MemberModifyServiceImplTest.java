package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.global.exception.external.badrequest.AuthWithInvalidPasswordException;
import com.liberty52.auth.service.controller.dto.ModifyRequestDto;
import com.liberty52.auth.service.controller.dto.ModifyResponseDto;
import com.liberty52.auth.service.entity.Auth;
import com.liberty52.auth.service.repository.AuthRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class MemberModifyServiceImplTest {
  private final String email = "test@gmail.com";
  private final String authId = "TESTER-001";
  private final String originPhoneNumber = "010-0101-0101";
  private final String updatePhoneNumber = "010-1234-5678";
  private final String originName = "김테스터";
  private final String updateName = "수정된테스터";
  private final String originPassword = "12341234";
  private final String updatePassword = "87654321";

  @Autowired
  MemberModifyService memberModifyService;

  @Autowired
  AuthRepository authRepository;


  @Test
  void getMemberInfo(){
    Auth auth = authRepository.findById(authId).get();
    ModifyResponseDto response = memberModifyService.getMemberInfo(auth.getId());

    Assertions.assertEquals(response.getEmail(),email);
    Assertions.assertEquals(response.getPhoneNumber(), originPhoneNumber);
    Assertions.assertEquals(response.getName(), originName);
  }

  @Test
  void modifyMemberInfo(){
    Auth auth = authRepository.findById(authId).get();
    ModifyRequestDto dto = new ModifyRequestDto(originPassword,updatePassword, updatePhoneNumber,updateName);
    memberModifyService.modifyMemberInfo(authId,dto,null);

    Assertions.assertEquals(auth.getPhoneNumber(), updatePhoneNumber);
    Assertions.assertEquals(auth.getName(), updateName);
  }

  @Test
  void modifyMemberInfo_AuthWithInvalidPasswordException(){
    ModifyRequestDto dto = new ModifyRequestDto("false_password",updatePassword, updatePhoneNumber,updateName);
    Assertions.assertThrows(AuthWithInvalidPasswordException.class,()-> memberModifyService.modifyMemberInfo(authId,dto,null));
  }
}
