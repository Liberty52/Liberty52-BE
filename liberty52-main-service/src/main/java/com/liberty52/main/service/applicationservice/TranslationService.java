package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.TranslationDto;

public interface TranslationService {
    TranslationDto.Response translateText(String authId, TranslationDto.Request dto);
}
