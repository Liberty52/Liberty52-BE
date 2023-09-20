package com.liberty52.product.service.applicationservice;

import org.springframework.web.multipart.MultipartFile;

public interface ProductIntroductionCreateService {
	void createProductIntroduction(String role, String productId, MultipartFile productIntroductionImageFile);
}
