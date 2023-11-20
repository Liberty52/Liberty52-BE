package com.liberty52.auth.service.fake;

import com.liberty52.auth.service.controller.dto.ReviewerProfileResponse;
import com.liberty52.auth.service.entity.Auth;
import com.liberty52.auth.service.entity.Role;
import com.liberty52.auth.service.repository.AuthRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class FakeAuthRepository implements AuthRepository {

    private static Map<String, Auth> storage = new HashMap<>();

    @Override
    public Optional<Auth> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<Auth> findByRefreshToken(String refreshToken) {
        return Optional.empty();
    }

    @Override
    public List<Auth> findEmailByNameAndPhoneNumber(String name, String phoneNumber) {
        return null;
    }

    @Override
    public Optional<Auth> findAuthAndSocialLoginByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<ReviewerProfileResponse> findReviewerProfileById(String authId) {
        return Optional.empty();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Auth> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Auth> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Auth> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Auth getOne(String s) {
        return null;
    }

    @Override
    public Auth getById(String s) {
        return null;
    }

    @Override
    public Auth getReferenceById(String s) {
        return null;
    }

    @Override
    public <S extends Auth> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Auth> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Auth> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Auth> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Auth> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Auth> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Auth, R> R findBy(Example<S> example,
            Function<FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Auth> S save(S entity) {
        storage.put(entity.getId(),entity);
        return entity;
    }

    @Override
    public <S extends Auth> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Auth> findById(String s) {
        return Optional.ofNullable(storage.get(s));
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Auth> findAll() {
        return null;
    }

    @Override
    public List<Auth> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {
        storage.remove(s);
    }

    @Override
    public void delete(Auth entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Auth> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Auth> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Auth> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Auth> findByRole(Role role, Pageable pageable) {
        return null;
    }
}
