package com.liberty52.auth.global.adapter;

import org.springframework.web.multipart.MultipartFile;

public interface S3Uploader {
    String upload(MultipartFile multipartFile);
}
