package com.liberty52.auth.service.applicationservice.impl;

import com.liberty52.auth.global.adapter.S3Uploader;
import com.liberty52.auth.service.applicationservice.QuestionImgSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionImgSaveServiceImpl implements QuestionImgSaveService {

    private final S3Uploader s3Uploader;

    @Override
    public String saveQuestionImage(MultipartFile imageFile) {
        return uploadImage(imageFile);
    }

    private String uploadImage(MultipartFile multipartFile) {
        if (multipartFile == null)
            return null;
        return s3Uploader.upload(multipartFile);
    }
}
