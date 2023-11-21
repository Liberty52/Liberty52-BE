package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.global.util.Validator;
import com.liberty52.main.service.applicationservice.OptionDetailModifyService;
import com.liberty52.main.service.controller.dto.OptionDetailModifyRequestDto;
import com.liberty52.main.service.entity.OptionDetail;
import com.liberty52.main.service.repository.OptionDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OptionDetailModifyServiceImpl implements OptionDetailModifyService {

    private final OptionDetailRepository optionDetailRepository;

    @Override
    public void modifyOptionDetailByAdmin(String role, String optionDetailId, OptionDetailModifyRequestDto dto) {
        Validator.isAdmin(role);
        OptionDetail optionDetail = optionDetailRepository.findById(optionDetailId).orElseThrow(() -> new ResourceNotFoundException("OptionDetail", "ID", optionDetailId));
        optionDetail.modify(dto.getName(), dto.getPrice(), dto.getOnSale(), dto.getStock());
    }

    public void modifyOptionDetailOnSailStateByAdmin(String role, String optionDetailId) {
        Validator.isAdmin(role);
        OptionDetail optionDetail = optionDetailRepository.findById(optionDetailId).orElseThrow(() -> new ResourceNotFoundException("OptionDetail", "ID", optionDetailId));
        optionDetail.updateOnSale();
    }
}
