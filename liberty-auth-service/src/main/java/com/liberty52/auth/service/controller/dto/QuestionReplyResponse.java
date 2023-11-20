package com.liberty52.auth.service.controller.dto;

import com.liberty52.auth.service.entity.QuestionReply;
import lombok.Data;

import java.time.LocalDate;

@Data
public class QuestionReplyResponse{

  private String replyId;
  private String replyContent;
  private String replyWriterId;
  private LocalDate replyCreatedAt;

  public QuestionReplyResponse(QuestionReply questionReply) {
    this.replyId = questionReply.getId();
    this.replyContent = questionReply.getContent();
    this.replyWriterId = questionReply.getWriterId();
    this.replyCreatedAt = questionReply.getCreatedAt().toLocalDate();
  }
}
