package com.liberty52.common.exception.external.notfound;

public class ResourceNotFoundException extends NotFoundException {
    public ResourceNotFoundException(String resourceName, String by, String param) {
        super(String.format("%s is not found by %s. The %s was %s", resourceName, by, by, param));
    }

    public ResourceNotFoundException(String resourceName) {
        super(String.format("%s is not exist.", resourceName));
    }
}
