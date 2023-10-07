package com.liberty52.product.global.aspect;

import com.liberty52.product.global.annotation.DistributedLock;
import com.liberty52.product.global.util.CustomSpringELParser;
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

    @Around("@annotation(com.liberty52.product.global.annotation.DistributedLock)")
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

        try {
            boolean available = lock.tryLock(dLock.waitTime(), dLock.leaseTime(), dLock.timeUnit());
            log.debug("[LB-LOG] Distributed Lock - {} get lock", key);
            if (!available) {
                return false;
            }
            return transaction.proceed(joinPoint);
        } catch (InterruptedException e) {
            log.error("Occurred InterruptedException during Redisson Lock\n", e);
            throw e;
        } finally {
            try {
                lock.unlock();
                log.debug("[LB-LOG] Distributed Lock - {} release lock", key);
            } catch (IllegalMonitorStateException e) {
                log.info("Redisson Lock Already UnLock. serviceName -> {}, key -> {}", method.getName(), key);
            }
        }
    }
}
