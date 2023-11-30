package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.CartModifyRequestDto;
import com.liberty52.main.service.controller.dto.CartModifyWithLicenseRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface CartItemModifyService {
    void modifyUserCartItem(String authId, CartModifyRequestDto dto, MultipartFile imageFile, String customProductId);

    void modifyGuestCartItem(String guestId, CartModifyRequestDto dto, MultipartFile imageFile, String customProductId);

    void modifyUserCartItemWihLicense(String authId, CartModifyWithLicenseRequestDto dto, String customProductId);

    void modifyGuestCartItemWithLicense(String guestId, CartModifyWithLicenseRequestDto dto, String customProductId);
}
