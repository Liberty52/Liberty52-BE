package com.liberty52.common.feign.error;

import com.liberty52.common.exception.external.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FeignRestExceptionHandler {

    @ExceptionHandler(FeignClientException.class)
    public ResponseEntity<ErrorResponse> handleFeignClientException(FeignClientException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.createErrorResponse(ex, request.getRequestURI()));
    }

}
