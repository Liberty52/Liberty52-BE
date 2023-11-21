package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.CreateOptionDetailRequestDto;

public interface OptionDetailCreateService {
    void createOptionDetailByAdmin(String role, CreateOptionDetailRequestDto dto, String optionId);
}
