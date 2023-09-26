package com.liberty52.product.service.applicationservice;

import org.springframework.web.multipart.MultipartFile;

public interface ProductIntroductionModifyService {
    void modifyProductIntroduction(String role, String productId, MultipartFile productIntroductionImageFile);
}
