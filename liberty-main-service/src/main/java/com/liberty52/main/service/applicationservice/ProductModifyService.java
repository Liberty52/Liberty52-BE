package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.ProductModifyRequestDto;
import com.liberty52.main.service.entity.Product;
import org.springframework.web.multipart.MultipartFile;

public interface ProductModifyService {
    Product modifyProductByAdmin(String role, String productId, ProductModifyRequestDto productRequestDto, MultipartFile productImage);
}
