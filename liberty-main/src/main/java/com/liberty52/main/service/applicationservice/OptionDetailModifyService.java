package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.OptionDetailModifyRequestDto;

public interface OptionDetailModifyService {
    void modifyOptionDetailByAdmin(String role, String optionDetailId, OptionDetailModifyRequestDto dto);

    void modifyOptionDetailOnSailStateByAdmin(String role, String optionDetailId);
}
