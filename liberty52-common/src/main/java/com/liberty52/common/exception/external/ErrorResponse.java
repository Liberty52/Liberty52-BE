package com.liberty52.common.exception.external;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private String timeStamp;
    private HttpStatus httpStatus;
    private String errorCode;
    private String errorName;
    private String errorMessage;
    private String path;

    public static ErrorResponse createErrorResponse(ErrorCode errorCode, String path) {
        return new ErrorResponse(
                LocalDateTime.now().toString(),
                errorCode.getHttpStatus(),
                errorCode.getErrorCode(),
                errorCode.getErrorName(),
                errorCode.getErrorMessage(),
                path
        );
    }

    public static ErrorResponse createErrorResponse(
            HttpStatus status,
            String errorCode,
            String errorName,
            String errorMessage,
            String path
    ) {
        return builder()
                .timeStamp(LocalDateTime.now().toString())
                .httpStatus(status)
                .errorCode(errorCode)
                .errorName(errorName)
                .errorMessage(errorMessage)
                .path(path)
                .build();
    }

    @Override
    public String toString() {
        return "{" +
                "timeStamp=" + timeStamp +
                ", httpStatus=" + httpStatus +
                ", errorCode='" + errorCode + '\'' +
                ", errorName='" + errorName + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
