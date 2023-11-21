package com.liberty52.auth.user.service;

import com.liberty52.auth.user.web.dto.ReviewerProfileResponse;

import java.util.Map;
import java.util.Set;

public interface ProfileRetrieveService {

    Map<String, ReviewerProfileResponse> retrieveReviewerProfile(Set<String> ids);
}
