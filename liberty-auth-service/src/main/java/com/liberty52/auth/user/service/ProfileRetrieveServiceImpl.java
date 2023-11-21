package com.liberty52.auth.user.service;

import com.liberty52.auth.user.repository.AuthRepository;
import com.liberty52.auth.user.web.dto.ReviewerProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@RequiredArgsConstructor
@Service
public class ProfileRetrieveServiceImpl implements
    ProfileRetrieveService {

    private final AuthRepository authRepository;

    @Override
    public Map<String, ReviewerProfileResponse> retrieveReviewerProfile(Set<String> ids) {
        Map<String, ReviewerProfileResponse> response = new HashMap<>();
        for (String id : ids)
            response.put(id, authRepository.findReviewerProfileById(id)
                    .orElseGet(() -> new ReviewerProfileResponse(id,null)));
        return response;
    }
}
