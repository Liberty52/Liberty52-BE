package com.liberty52.auth.service.repository;

import com.liberty52.auth.service.entity.QuestionReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionReplyRepository extends JpaRepository<QuestionReply, String> {
}