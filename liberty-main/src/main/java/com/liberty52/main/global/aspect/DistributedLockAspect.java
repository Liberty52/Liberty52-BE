package com.liberty52.main.global.aspect;

import com.liberty52.main.global.annotation.DistributedLock;
import com.liberty52.main.global.exception.external.badrequest.DistributedLockException;
import com.liberty52.main.global.util.CustomSpringELParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAspect {

    private static final String REDISSON_LOCK_PREFIX = "LOCK:";

    private final RedissonClient redissonClient;
    private final DistributedLockTransaction transaction;

    @Around("@annotation(com.liberty52.main.global.annotation.DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock dLock = method.getAnnotation(DistributedLock.class);

        String key = REDISSON_LOCK_PREFIX +
                CustomSpringELParser.getDynamicValue(
                        signature.getParameterNames(),
                        joinPoint.getArgs(),
                        dLock.key());

        RLock lock = redissonClient.getLock(key);

        long start = System.currentTimeMillis();
        try {
            boolean available = lock.tryLock(dLock.waitTime(), dLock.leaseTime(), dLock.timeUnit());
            log.info("[Distributed Lock] - {} get lock", key);
            if (!available) {
                throw new DistributedLockException();
            }
            return transaction.proceed(joinPoint);
        } catch (InterruptedException e) {
            log.error("[Distributed Lock] - Occurred InterruptedException during Redisson Lock\n", e);
            throw e;
        } finally {
            try {
                lock.unlock();
                long end = System.currentTimeMillis();
                log.info("[Distributed Lock] - {} release lock. Lock using on {}ms", key, end - start);
            } catch (IllegalMonitorStateException e) {
                log.info("[Distributed Lock] - Already UnLock. serviceName -> {}, key -> {}", method.getName(), key);
            }
        }
    }
}
