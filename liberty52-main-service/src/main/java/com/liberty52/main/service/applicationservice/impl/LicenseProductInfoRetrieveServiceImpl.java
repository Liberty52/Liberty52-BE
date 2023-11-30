package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.common.exception.external.badrequest.BadRequestException;
import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.global.util.Validator;
import com.liberty52.main.service.applicationservice.LicenseProductInfoRetrieveService;
import com.liberty52.main.service.controller.dto.LicenseOptionInfoResponseDto;
import com.liberty52.main.service.controller.dto.LicenseOptionResponseDto;
import com.liberty52.main.service.entity.Product;
import com.liberty52.main.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LicenseProductInfoRetrieveServiceImpl implements LicenseProductInfoRetrieveService {
    private final ProductRepository productRepository;

    @Override
    public LicenseOptionInfoResponseDto retrieveLicenseProductOptionInfoListByAdmin(String role,
                                                                                    String productId, boolean onSale) {
        Validator.isAdmin(role);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product", "id", productId));
        if (product.getLicenseOption() == null) {
            throw new BadRequestException("라이선스 상품에 옵션이 존재하지 않습니다.");
        }
        return new LicenseOptionInfoResponseDto(product.getLicenseOption(), onSale);
    }

    @Override
    public LicenseOptionResponseDto retrieveLicenseProductOptionInfo(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product", "id", productId));
        if (product.getLicenseOption() != null) {
            return new LicenseOptionResponseDto(product.getLicenseOption());
        } else {
            return null;
        }

    }
}
