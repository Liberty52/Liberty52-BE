package com.liberty52.product.global.adapter;

import com.liberty52.product.global.exception.internal.S3UploaderException;
import org.springframework.web.multipart.MultipartFile;

public interface S3Uploader {
    String upload(MultipartFile multipartFile) throws S3UploaderException;
    void delete(String imageUrl);
    String uploadOrNull(MultipartFile multipartFile);
    String uploadOrThrowApiException(MultipartFile multipartFile);
    String uploadOrEmpty(MultipartFile multipartFile);
}
