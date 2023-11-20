package com.liberty52.auth.service.repository;

import com.liberty52.auth.service.applicationservice.impl.PasswordMailServiceImpl;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

@EnableRedisRepositories
public interface EmailTokenByUpdatePasswordRepository extends CrudRepository<PasswordMailServiceImpl.EmailTokenVO, String> {
}
