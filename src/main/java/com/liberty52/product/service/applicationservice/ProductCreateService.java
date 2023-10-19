package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.controller.dto.ProductRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface ProductCreateService {
    void createProductByAdmin(String role, ProductRequestDto productRequestDto, MultipartFile productImage);
}
