package com.liberty52.auth.notice.repository;

import com.liberty52.auth.notice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, String> {
    Optional<Notice> findByTitle(String title);

    Page<Notice> findAllByOrderByCreatedAtDesc(Pageable pageable);

}