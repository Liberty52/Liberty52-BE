package com.liberty52.main.global.adapter.s3;

import com.liberty52.main.global.adapter.StoragePresigner;
import com.liberty52.main.global.exception.internal.S3UploaderException;
import org.springframework.web.multipart.MultipartFile;

public interface S3Uploader extends S3Uploader_, StoragePresigner {
    String upload(MultipartFile multipartFile) throws S3UploaderException;

    String uploadOfByte(byte[] b);
}
