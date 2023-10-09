package com.liberty52.product.global.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract sealed class Result<T> {
    public abstract T getOrThrow() throws Throwable;

    public abstract T getOrThrows() throws RuntimeException;

    public abstract Result<T> onSuccess(Consumer<T> consumer);

    public abstract Result<T> onFailure(Consumer<Throwable> consumer);

    public abstract T getOrElse(Function<Throwable, T> func);

    public abstract T getOrDefault(T defaultValue);

    public abstract Boolean isSuccess();

    public abstract Boolean isFailure();

    public abstract T getOrNull();

    public abstract Throwable exceptionOrNull();

    public abstract <K> Result<K> map(Function<T, K> transform);

    public abstract <K> Result<K> mapCatching(Function<T, K> transform);

    public abstract Result<T> recover(Function<Throwable, T> transform);

    public abstract Result<T> recoverCatching(Function<Throwable, T> transform);

    public static <T> Result<T> success(T value) {
        return new Success<>(value);
    }

    public static <T> Result<T> failure(Throwable throwable) {
        return new Failure<>(throwable);
    }

    public static <T> Result<T> runCatching(Supplier<T> supplier) {
        try {
            return success(supplier.get());
        } catch (Throwable t) {
            return failure(t);
        }
    }

    public abstract T getOrElseThrow(Function<Throwable, RuntimeException> func) throws RuntimeException;

    public abstract T getOrElseThrow(Supplier<RuntimeException> supplier) throws RuntimeException;

    public static final class Success<T> extends Result<T> {
        private final T value;

        public Success(T value) {
            this.value = value;
        }

        @Override
        public T getOrThrow() {
            return value;
        }

        @Override
        public T getOrThrows() {
             return value;
        }

        @Override
        public Result<T> onSuccess(Consumer<T> consumer) {
            consumer.accept(value);
            return this;
        }

        @Override
        public Result<T> onFailure(Consumer<Throwable> consumer) {
            return this;
        }

        @Override
        public T getOrElse(Function<Throwable, T> func) {
            return value;
        }

        @Override
        public T getOrDefault(T defaultValue) {
            return value;
        }

        @Override
        public Boolean isSuccess() {
            return true;
        }

        @Override
        public Boolean isFailure() {
            return false;
        }

        @Override
        public T getOrNull() {
            return value;
        }

        @Override
        public Throwable exceptionOrNull() {
            return null;
        }

        @Override
        public <K> Result<K> map(Function<T, K> transform) {
            return success(transform.apply(value));
        }

        @Override
        public <K> Result<K> mapCatching(Function<T, K> transform) {
            try {
                return map(transform);
            } catch (Throwable throwable) {
                return failure(throwable);
            }
        }

        @Override
        public Result<T> recover(Function<Throwable, T> transform) {
            return this;
        }

        @Override
        public Result<T> recoverCatching(Function<Throwable, T> transform) {
            return this;
        }

        @Override
        public T getOrElseThrow(Function<Throwable, RuntimeException> func) throws RuntimeException {
            return value;
        }

        @Override
        public T getOrElseThrow(Supplier<RuntimeException> supplier) throws RuntimeException {
            return value;
        }

        @Override
        public String toString() {
            return "Success{" +
                "value=" + value +
                '}';
        }
    }

    public static final class Failure<T> extends Result<T> {
        private final Throwable error;
        private final RuntimeException runtimeException;

        public Failure(Throwable throwable) {
            this.error = throwable;
            this.runtimeException = (RuntimeException) throwable;
        }

        @Override
        public T getOrThrow() throws Throwable {
            throw error;
        }

        @Override
        public T getOrThrows() throws RuntimeException {
            throw runtimeException;
        }

        @Override
        public Result<T> onSuccess(Consumer<T> consumer) {
            return this;
        }

        @Override
        public Result<T> onFailure(Consumer<Throwable> consumer) {
            consumer.accept(error);
            return this;
        }

        @Override
        public T getOrElse(Function<Throwable, T> func) {
            return func.apply(error);
        }

        @Override
        public T getOrDefault(T defaultValue) {
            return defaultValue;
        }

        @Override
        public Boolean isSuccess() {
            return false;
        }

        @Override
        public Boolean isFailure() {
            return true;
        }

        @Override
        public T getOrNull() {
            return null;
        }

        @Override
        public Throwable exceptionOrNull() {
            return error;
        }

        @Override
        public <K> Result<K> map(Function<T, K> transform) {
            return failure(error);
        }

        @Override
        public <K> Result<K> mapCatching(Function<T, K> transform) {
            try {
                return map(transform);
            } catch (Throwable t) {
                return failure(t);
            }
        }

        @Override
        public Result<T> recover(Function<Throwable, T> transform) {
            return success(transform.apply(error));
        }

        @Override
        public Result<T> recoverCatching(Function<Throwable, T> transform) {
            try {
                return recover(transform);
            } catch (Throwable t) {
                return failure(t);
            }
        }

        @Override
        public T getOrElseThrow(Function<Throwable, RuntimeException> func) throws RuntimeException {
            throw func.apply(error);
        }

        @Override
        public T getOrElseThrow(Supplier<RuntimeException> supplier) throws RuntimeException {
            throw supplier.get();
        }

        @Override
        public String toString() {
            return "Failure{" +
                "error=" + error +
                '}';
        }
    }
}
