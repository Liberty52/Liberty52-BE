package com.liberty52.auth.global.exception.external.notfound;

import com.liberty52.common.exception.external.notfound.NotFoundException;

public class ResourceNotFoundException extends NotFoundException {
    public ResourceNotFoundException(String resourceName, String by, String param) {
        super(String.format("%s is not found by %s. The %s was %s", resourceName, by, by, param));
    }
}
