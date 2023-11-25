package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.CartItemRequest;
import com.liberty52.main.service.controller.dto.CartItemRequestWithLicense;
import org.springframework.web.multipart.MultipartFile;

public interface CartItemCreateService {
    void createAuthCartItem(String authId, MultipartFile imageFile, CartItemRequest dto);

    void createGuestCartItem(String guestId, MultipartFile imageFile, CartItemRequest dto);

    void createAuthCartItemWithLicense(String authId, CartItemRequestWithLicense dto);

    void createGuestCartItemWithLicense(String guestId, CartItemRequestWithLicense dto);
}
