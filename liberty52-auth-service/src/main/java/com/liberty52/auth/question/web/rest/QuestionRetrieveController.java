package com.liberty52.auth.question.web.rest;

import com.liberty52.auth.question.service.QuestionRetrieveService;
import com.liberty52.auth.question.web.dto.AdminQuestionRetrieveResponse;
import com.liberty52.auth.question.web.dto.QuestionDetailResponseDto;
import com.liberty52.auth.question.web.dto.QuestionRetrieveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class QuestionRetrieveController {
  private final QuestionRetrieveService questionRetrieveService;
  @GetMapping("/questions")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<QuestionRetrieveResponseDto> retrieveQuestion(@RequestHeader(HttpHeaders.AUTHORIZATION) String writerId,
                                                                      @RequestParam(value = "page", defaultValue = "0") int pageNumber,
                                                                      @RequestParam(value = "size", defaultValue = "10") int size){
    return ResponseEntity.ok(questionRetrieveService.retrieveQuestions(writerId,pageNumber,size));
  }
  @GetMapping("/questions/{questionId}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<QuestionDetailResponseDto> retrieveQuestionDetail(@PathVariable("questionId") String questionId,
                                                                          @RequestHeader(HttpHeaders.AUTHORIZATION) String writerId){
    return ResponseEntity.ok(questionRetrieveService.retrieveQuestionDetail(questionId,writerId));
  }

  //admin
  @GetMapping("/admin/questions")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<AdminQuestionRetrieveResponse> retrieveQuestionByAdmin(@RequestHeader("LB-Role") String role,
                                                                               @RequestParam(value = "page", defaultValue = "0") int pageNumber,
                                                                               @RequestParam(value = "size", defaultValue = "10") int size){
    return ResponseEntity.ok(questionRetrieveService.retrieveQuestionByAdmin(role,pageNumber,size));
  }

  @GetMapping("/admin/questions/{questionId}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<QuestionDetailResponseDto> retrieveQuestionDetailByAdmin(@RequestHeader("LB-Role") String role,
      @PathVariable("questionId") String questionId){
    return ResponseEntity.ok(questionRetrieveService.retrieveQuestionDetailByAdmin(role,questionId));
  }
}
