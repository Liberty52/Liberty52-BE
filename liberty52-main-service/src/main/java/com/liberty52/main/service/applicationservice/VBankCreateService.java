package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.VBankDto;

public interface VBankCreateService {

    VBankDto createVBankByAdmin(String role, String bank, String account, String holder);

}
