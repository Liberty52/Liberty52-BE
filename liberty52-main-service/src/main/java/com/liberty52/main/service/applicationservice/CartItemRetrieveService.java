package com.liberty52.main.service.applicationservice;

import com.liberty52.main.service.controller.dto.CartItemResponse;

import java.util.List;

public interface CartItemRetrieveService {
    List<CartItemResponse> retrieveAuthCartItem(String authId);

    List<CartItemResponse> retrieveGuestCartItem(String guestId);
}
