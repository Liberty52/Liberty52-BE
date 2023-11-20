package com.liberty52.auth.service.applicationservice.impl;

import com.liberty52.auth.global.exception.external.badrequest.InvalidIdOrPhoneNumberException;
import com.liberty52.auth.service.applicationservice.MemberFindService;
import com.liberty52.auth.service.controller.dto.FindRequestDto;
import com.liberty52.auth.service.entity.Auth;
import com.liberty52.auth.service.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberFindServiceImpl implements MemberFindService {

  private final AuthRepository authRepository;

  @Override
  public List<String> findEmailByNameAndPhoneNumber(FindRequestDto dto) {
    List<Auth> authList = authRepository.findEmailByNameAndPhoneNumber(dto.getName(), dto.getPhoneNumber());
    if (authList.isEmpty()) {
      throw new InvalidIdOrPhoneNumberException();
    }
    return authList.stream()
        .map(Auth::getEmail)
        .toList();
  }
}
