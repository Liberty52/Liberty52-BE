package com.liberty52.auth.global.exception.external.badrequest;

public class FileTypeIsNotImageException extends BadRequestException {
    public FileTypeIsNotImageException() {
        super("이미지 형식의 파일만 업로드할 수 있습니다.");
    }
}
