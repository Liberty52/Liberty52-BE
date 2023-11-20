package com.liberty52.auth.service.controller.dto;

import com.liberty52.auth.service.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Getter
@AllArgsConstructor
public class AdminQuestionRetrieveResponse {
  private List<QuestionContent> contents;
  private long currentPage;
  private long startPage;
  private long lastPage;
  private long totalPage;

  public AdminQuestionRetrieveResponse(List<Question> questionList, List<String> emails,
      long currentPage, long startPage, long lastPage, long totalPage) {
    contents = IntStream.range(0, questionList.size())
        .mapToObj(i -> {
          Question question = questionList.get(i);
          String email = emails.get(i);
          return new QuestionContent(
              question.getId(),
              question.getStatus().name(),
              question.getTitle(),
              question.getContent(),
              question.getWriterId(),
              question.getCreatedAt().toLocalDate(),
              email
          );
        })
        .toList();

    this.currentPage = currentPage;
    this.startPage = startPage;
    this.lastPage = lastPage;
    this.totalPage = totalPage;
  }

  @Data
  public class QuestionContent{
    private String id;
    private String status;
    private String title;
    private String content;
    private String writerId;
    private LocalDate createdAt;
    private String email;

    public QuestionContent(String id,String status,String title, String content,
        String writerId,LocalDate createAt,String email){
      this.id = id;
      this.status = status;
      this.title = title;
      this.content = content;
      this.writerId = writerId;
      this.createdAt = createAt;
      this.email = email;
    }
  }
}
