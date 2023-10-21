package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.controller.dto.ProductRequestDto;
import com.liberty52.product.service.entity.Product;
import org.springframework.web.multipart.MultipartFile;

public interface ProductCreateService {
    Product createProductByAdmin(String role, ProductRequestDto productRequestDto, MultipartFile productImage);
}
