package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.common.exception.external.badrequest.BadRequestException;
import com.liberty52.main.global.util.Validator;
import com.liberty52.main.service.applicationservice.VBankCreateService;
import com.liberty52.main.service.controller.dto.VBankDto;
import com.liberty52.main.service.entity.payment.BankType;
import com.liberty52.main.service.entity.payment.VBank;
import com.liberty52.main.service.repository.VBankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VBankCreateServiceImpl implements VBankCreateService {

    private final VBankRepository vBankRepository;

    @Override
    @Transactional
    public VBankDto createVBankByAdmin(String role, String bank, String account, String holder) {
        Validator.isAdmin(role);

        BankType bankType = BankType.getBankType(bank);
        validateCreateVBank(bankType, account, holder);

        return VBankDto.fromEntity(vBankRepository.save(VBank.of(bankType, account, holder)));
    }

    private void validateCreateVBank(BankType bankType, String account, String holder) {
        if (vBankRepository.existsByBankAndAccountAndHolder(bankType, account, holder)) {
            throw new BadRequestException("이미 등록되어 있는 가상계좌입니다.");
        }
    }
}
