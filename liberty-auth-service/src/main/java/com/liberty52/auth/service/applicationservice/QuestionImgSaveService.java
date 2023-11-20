package com.liberty52.auth.service.applicationservice;

import org.springframework.web.multipart.MultipartFile;

public interface QuestionImgSaveService {
    String saveQuestionImage(MultipartFile imageFile);
}
