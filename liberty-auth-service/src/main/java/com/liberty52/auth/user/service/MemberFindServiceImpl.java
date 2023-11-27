package com.liberty52.auth.user.service;

import com.liberty52.auth.global.exception.external.badrequest.InvalidIdOrPhoneNumberException;
import com.liberty52.auth.user.entity.Auth;
import com.liberty52.auth.user.repository.AuthRepository;
import com.liberty52.auth.user.web.dto.FindRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Slf4j
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

    @Override
    public Map<String, String> getUserInfosByWriterIds(List<String> writerIds) {
        return authRepository.findAllById(writerIds).stream()
                .collect(Collectors.toMap(Auth::getId, Auth::getEmail));
    }
}
