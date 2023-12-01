package com.liberty52.auth.question.service;

import org.springframework.web.multipart.MultipartFile;

public interface QuestionImgSaveService {
    String saveQuestionImage(MultipartFile imageFile);
}
