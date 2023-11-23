package com.liberty52.auth.question.repository;

import com.liberty52.auth.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, String> {
  List<Question> findByWriterId(String writerId);

  Page<Question> findAllByWriterId(String writerId, PageRequest pageable);

  void deleteAllByWriterId(String writerId);
}
