package com.liberty52.auth.global.exception.external.internalservererror;

public class FileConvertException extends InternalServerErrorException {
    public FileConvertException() {
        super("파일 변환 작업에서 오류가 발생하였습니다.");
    }
}
