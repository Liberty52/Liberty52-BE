package com.liberty52.auth.service.repository;

import com.liberty52.auth.service.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, String> {
    Optional<Notice> findByTitle(String title);

    Page<Notice> findAllByOrderByCreatedAtDesc(Pageable pageable);

}