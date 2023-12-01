package com.liberty52.main.global.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public sealed interface Result<T> {
    static <T> Result<T> success(T value) {
        return new Success<>(value);
    }

    static <T> Result<T> failure(Throwable throwable) {
        return new Failure<>(throwable);
    }

    static <T> Result<T> runCatching(Supplier<T> supplier) {
        try {
            return success(supplier.get());
        } catch (Throwable t) {
            return failure(t);
        }
    }

    T getOrThrow() throws RuntimeException;

    Result<T> onSuccess(Consumer<T> consumer);

    Result<T> onFailure(Consumer<Throwable> consumer);

    T getOrElse(Function<Throwable, T> func);

    T getOrDefault(T defaultValue);

    Boolean isSuccess();

    Boolean isFailure();

    T getOrNull();

    Throwable exceptionOrNull();

    <K> Result<K> map(Function<T, K> transform);

    <K> Result<K> mapCatching(Function<T, K> transform);

    Result<T> recover(Function<Throwable, T> transform);

    Result<T> recoverCatching(Function<Throwable, T> transform);

    T getOrElseThrow(Function<Throwable, RuntimeException> func) throws RuntimeException;

    T getOrElseThrow(Supplier<RuntimeException> supplier) throws RuntimeException;

    final class Success<T> implements Result<T> {
        private final T value;

        public Success(T value) {
            this.value = value;
        }

        @Override
        public T getOrThrow() {
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
            var ex = exceptionOrNull();
            if (ex == null) {
                return this;
            } else {
                return Result.success(transform.apply(exceptionOrNull()));
            }
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

    final class Failure<T> implements Result<T> {
        private final RuntimeException error;

        public Failure(Throwable throwable) {
            if (throwable instanceof RuntimeException e) {
                this.error = e;
            } else {
                this.error = new RuntimeException(throwable);
            }
        }

        @Override
        public T getOrThrow() throws RuntimeException {
            throw error;
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
