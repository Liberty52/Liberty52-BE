package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.main.global.adapter.s3.S3UploaderApi;
import com.liberty52.main.global.util.Validator;
import com.liberty52.main.service.applicationservice.ProductIntroductionImgSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductIntroductionImgSaveServiceImpl implements ProductIntroductionImgSaveService {
    private final S3UploaderApi s3Uploader;

    @Override
    public String saveProductIntroductionImageByAdmin(String role, MultipartFile file) {
        Validator.isAdmin(role);
        return uploadImage(file);
    }

    private String uploadImage(MultipartFile multipartFile) {
        if (multipartFile == null)
            return null;
        return s3Uploader.upload(multipartFile);
    }
}
