package com.liberty52.auth.question.service;

import com.liberty52.auth.question.web.dto.AdminQuestionRetrieveResponse;
import com.liberty52.auth.question.web.dto.QuestionDetailResponseDto;
import com.liberty52.auth.question.web.dto.QuestionRetrieveResponseDto;

public interface QuestionRetrieveService {

  QuestionRetrieveResponseDto retrieveQuestions(String writerId, int page, int size);
  QuestionDetailResponseDto retrieveQuestionDetail(String questionId, String writerId);

  //admin
  AdminQuestionRetrieveResponse retrieveQuestionByAdmin(String role, int pageNumber, int size);
  QuestionDetailResponseDto retrieveQuestionDetailByAdmin(String role, String questionId);
}
