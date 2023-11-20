package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.ProductCreateRequestDto;
import com.liberty52.main.service.entity.Product;
import org.springframework.web.multipart.MultipartFile;

public interface ProductCreateService {
    Product createProductByAdmin(String role, ProductCreateRequestDto productRequestDto, MultipartFile productImage);
}
