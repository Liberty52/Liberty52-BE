package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.service.controller.dto.ReviewerProfileResponse;

import java.util.Map;
import java.util.Set;

public interface ProfileRetrieveService {

    Map<String, ReviewerProfileResponse> retrieveReviewerProfile(Set<String> ids);
}
