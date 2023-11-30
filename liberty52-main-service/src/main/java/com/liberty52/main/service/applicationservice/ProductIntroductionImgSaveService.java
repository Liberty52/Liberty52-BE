package com.liberty52.main.service.applicationservice;

import org.springframework.web.multipart.MultipartFile;

public interface ProductIntroductionImgSaveService {
    String saveProductIntroductionImageByAdmin(String role, MultipartFile file);
}
