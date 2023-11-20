package com.liberty52.main.global.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DistributedLockTransaction {

    /**
     * 더티 커밋 같은 문제가 없도록 데이터 정합성을 보장하기 위해 <br>
     * 분산락에 해당하는 로직을 서로 다른 트랜잭션으로 실행한다.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Object proceed(final ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }

}
