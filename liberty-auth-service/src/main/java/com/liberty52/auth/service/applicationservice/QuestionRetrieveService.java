package com.liberty52.auth.service.applicationservice;

import com.liberty52.auth.service.controller.dto.AdminQuestionRetrieveResponse;
import com.liberty52.auth.service.controller.dto.QuestionDetailResponseDto;
import com.liberty52.auth.service.controller.dto.QuestionRetrieveResponseDto;

public interface QuestionRetrieveService {

  QuestionRetrieveResponseDto retrieveQuestions(String writerId, int page, int size);
  QuestionDetailResponseDto retrieveQuestionDetail(String questionId, String writerId);

  //admin
  AdminQuestionRetrieveResponse retrieveQuestionByAdmin(String role, int pageNumber, int size);
  QuestionDetailResponseDto retrieveQuestionDetailByAdmin(String role, String questionId);
}
