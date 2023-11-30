package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.ProductOptionModifyRequestDto;

public interface ProductOptionModifyService {
    void modifyProductOptionByAdmin(String role, String productOptionId, ProductOptionModifyRequestDto dto);

    void modifyProductOptionOnSailStateByAdmin(String role, String productOptionId);

}
