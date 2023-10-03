package com.liberty52.product.global.util;

import org.springframework.stereotype.Component;

@Component
public class _ThreadManager implements ThreadManager {
    @Override
    public boolean sleep(long millis) {
        try {
            Thread.sleep(millis);
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }
}
