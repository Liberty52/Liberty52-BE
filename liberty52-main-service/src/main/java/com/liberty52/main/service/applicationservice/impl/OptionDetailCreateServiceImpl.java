package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.global.util.Validator;
import com.liberty52.main.service.applicationservice.OptionDetailCreateService;
import com.liberty52.main.service.controller.dto.CreateOptionDetailRequestDto;
import com.liberty52.main.service.entity.OptionDetail;
import com.liberty52.main.service.entity.ProductOption;
import com.liberty52.main.service.repository.OptionDetailRepository;
import com.liberty52.main.service.repository.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OptionDetailCreateServiceImpl implements OptionDetailCreateService {

    private final ProductOptionRepository productOptionRepository;
    private final OptionDetailRepository optionDetailRepository;

    @Override
    public void createOptionDetailByAdmin(String role, CreateOptionDetailRequestDto dto, String optionId) {
        Validator.isAdmin(role);
        ProductOption productOption = productOptionRepository.findById(optionId).orElseThrow(() -> new ResourceNotFoundException("option", "id", optionId));
        OptionDetail optionDetail = OptionDetail.create(dto.getName(), dto.getPrice(), dto.getOnSale(), 0);
        optionDetail.associate(productOption);
        optionDetailRepository.save(optionDetail);
    }
}
