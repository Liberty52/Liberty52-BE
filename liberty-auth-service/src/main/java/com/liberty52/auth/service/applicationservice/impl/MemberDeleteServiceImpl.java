package com.liberty52.auth.service.applicationservice.impl;


import com.liberty52.auth.global.event.Events;
import com.liberty52.auth.global.event.events.MemberDeletedEvent;
import com.liberty52.auth.global.exception.external.unauthorized.AuthNotFoundException;
import com.liberty52.auth.service.applicationservice.MemberDeleteService;
import com.liberty52.auth.service.entity.Auth;
import com.liberty52.auth.service.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberDeleteServiceImpl implements
    MemberDeleteService {

    private final AuthRepository authRepository;

    @Override
    public void deleteMemberByUserId(String userId) {
        Auth auth = authRepository.findById(userId)
                .orElseThrow(AuthNotFoundException::new);
        authRepository.delete(auth);

        Events.raise(new MemberDeletedEvent(auth.getEmail(), auth.getName(),userId));
    }
}
