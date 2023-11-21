package com.liberty52.auth.user.web.rest;


import com.liberty52.auth.user.service.ProfileRetrieveService;
import com.liberty52.auth.user.web.dto.ReviewerProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class ProfileRetrieveController {

    private final ProfileRetrieveService profileRetrieveService;

    @PostMapping("/info")
    public ResponseEntity<Map<String, ReviewerProfileResponse>> retrieveReviewerProfile(@RequestBody Set<String> ids){
        return ResponseEntity.ok(profileRetrieveService.retrieveReviewerProfile(ids));
    }

}
