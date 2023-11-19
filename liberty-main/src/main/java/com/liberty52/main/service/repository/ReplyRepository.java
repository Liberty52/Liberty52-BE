package com.liberty52.main.service.repository;

import com.liberty52.main.service.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, String> {
}