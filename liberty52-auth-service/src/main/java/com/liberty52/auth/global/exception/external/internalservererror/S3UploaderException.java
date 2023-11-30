package com.liberty52.auth.global.exception.external.internalservererror;

public class S3UploaderException extends InternalServerErrorException {
    public S3UploaderException(String msg) {
        super(msg);
    }
    public S3UploaderException() {
        super("파일 업로드 작업에 오류가 발생하였습니다.");
    }

}
