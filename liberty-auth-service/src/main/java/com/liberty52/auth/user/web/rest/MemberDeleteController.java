package com.liberty52.auth.user.web.rest;

import com.liberty52.auth.user.service.MemberDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberDeleteController {

    private final MemberDeleteService memberDeleteService;
    @DeleteMapping("/member")
    public ResponseEntity<Void> deleteMemberByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String userId){
        memberDeleteService.deleteMemberByUserId(userId);
        return ResponseEntity.ok().build();
    }
}
