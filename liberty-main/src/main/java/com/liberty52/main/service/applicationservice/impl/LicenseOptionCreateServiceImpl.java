package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.main.global.exception.external.badrequest.BadRequestException;
import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.global.util.Validator;
import com.liberty52.main.service.applicationservice.LicenseOptionCreateService;
import com.liberty52.main.service.controller.dto.LicenseOptionCreateRequestDto;
import com.liberty52.main.service.entity.Product;
import com.liberty52.main.service.entity.license.LicenseOption;
import com.liberty52.main.service.repository.LicenseOptionRepository;
import com.liberty52.main.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LicenseOptionCreateServiceImpl implements LicenseOptionCreateService {

    private final ProductRepository productRepository;
    private final LicenseOptionRepository licenseOptionRepository;

    @Override
    public void createLicenseOptionByAdmin(String role, LicenseOptionCreateRequestDto dto, String productId) {
        Validator.isAdmin(role);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("productId", "id", productId));
        if (product.isCustom()) {
            throw new BadRequestException("커스텀 상품에는 라이선스 옵션을 생성할 수 없습니다");
        }
        if (licenseOptionRepository.findByProductId(productId).isPresent()) {
            throw new BadRequestException("이미 라이선스 옵션이 생성되어 있습니다");
        }

        LicenseOption licenseOption = LicenseOption.create(dto.getName());
        licenseOption.associate(product);
    }
}
