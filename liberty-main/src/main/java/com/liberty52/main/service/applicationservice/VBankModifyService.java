package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.VBankDto;

public interface VBankModifyService {

    VBankDto updateVBankByAdmin(String role, String vBankId, String bank, String accountNumber, String holder);

}
