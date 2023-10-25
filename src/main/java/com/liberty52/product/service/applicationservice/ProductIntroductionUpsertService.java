package com.liberty52.product.service.applicationservice;

public interface ProductIntroductionUpsertService {
	void upsertProductIntroductionByAdmin(String role, String productId, String content);
}
