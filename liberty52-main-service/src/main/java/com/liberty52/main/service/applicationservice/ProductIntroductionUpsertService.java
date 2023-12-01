package com.liberty52.main.service.applicationservice;

public interface ProductIntroductionUpsertService {
    void upsertProductIntroductionByAdmin(String role, String productId, String content);
}
