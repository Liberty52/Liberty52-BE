package com.liberty52.auth.question.repository;

import com.liberty52.auth.question.entity.QuestionReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionReplyRepository extends JpaRepository<QuestionReply, String> {
}
