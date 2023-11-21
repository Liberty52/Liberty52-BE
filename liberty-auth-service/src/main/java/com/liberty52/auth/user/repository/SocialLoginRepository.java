package com.liberty52.auth.user.repository;

import com.liberty52.auth.user.entity.SocialLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialLoginRepository extends JpaRepository<SocialLogin, String> {
    Optional<SocialLogin> findByEmail(String email);
}

