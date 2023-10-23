package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.controller.dto.ProductCreateRequestDto;
import com.liberty52.product.service.controller.dto.ProductModifyRequestDto;
import com.liberty52.product.service.entity.Product;
import org.springframework.web.multipart.MultipartFile;

public interface ProductModifyService {
    Product modifyProductByAdmin(String role, String productId, ProductModifyRequestDto productRequestDto, MultipartFile productImage);
}
