package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.main.service.applicationservice.CartItemRetrieveService;
import com.liberty52.main.service.controller.dto.CartItemResponse;
import com.liberty52.main.service.controller.dto.CartOptionResponse;
import com.liberty52.main.service.controller.dto.LicenseOptionResponse;
import com.liberty52.main.service.entity.*;
import com.liberty52.main.service.entity.license.LicenseOptionDetail;
import com.liberty52.main.service.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemRetrieveServiceImpl implements CartItemRetrieveService {

    private final CartRepository cartRepository;

    @Override
    public List<CartItemResponse> retrieveAuthCartItem(String authId) {
        List<CartItemResponse> cartItemResponseList = new ArrayList<>();
        Cart cart = cartRepository.findByAuthId(authId).orElse(null);

        if (cart == null || cart.getCustomProducts().size() == 0) {
            return cartItemResponseList;
        }
        return retrieveCartItem(cartItemResponseList, cart);
    }

    @Override
    public List<CartItemResponse> retrieveGuestCartItem(String guestId) {
        List<CartItemResponse> cartItemResponseList = new ArrayList<>();
        Cart cart = cartRepository.findByAuthIdAndExpiryDateGreaterThanEqual(guestId, LocalDate.now()).orElse(null);

        if (cart == null || cart.getCustomProducts().size() == 0) {
            return cartItemResponseList;
        }
        return retrieveCartItem(cartItemResponseList, cart);
    }

    private List<CartItemResponse> retrieveCartItem(List<CartItemResponse> cartItemResponseList, Cart cart) {
        List<CustomProduct> cartItemList = cart.getCustomProducts();

        for (CustomProduct cartItem : cartItemList) {
            Product product = cartItem.getProduct();
            CartItemResponse cartItemResponse;
            if(product.isCustom()){
                cartItemResponse  = CartItemResponse.of(cartItem.getId(), product.getName(), cartItem.getUserCustomPictureUrl(), product.getPrice(), cartItem.getQuantity(), getCartOptionList(cartItem.getOptions()), product.getDeliveryOption().getCourierName(),product.getDeliveryOption().getFee(), product.isCustom(), null);

            } else {
                LicenseOptionDetail licenseOptionDetail = cartItem.getCustomLicenseOption().getLicenseOptionDetail();
                LicenseOptionResponse license = LicenseOptionResponse.of(licenseOptionDetail.getLicenseOption().getId(), licenseOptionDetail.getLicenseOption().getName(), licenseOptionDetail.getId(), licenseOptionDetail.getArtName(), licenseOptionDetail.getPrice());
                cartItemResponse  = CartItemResponse.of(cartItem.getId(), product.getName(), licenseOptionDetail.getArtUrl(), product.getPrice(), cartItem.getQuantity(), getCartOptionList(cartItem.getOptions()), product.getDeliveryOption().getCourierName(),product.getDeliveryOption().getFee(), product.isCustom(), license);
            }
            cartItemResponseList.add(cartItemResponse);
        }
        return cartItemResponseList;
    }

    private List<CartOptionResponse> getCartOptionList(List<CustomProductOption> options) {
        if (options.size() == 0) {
            return null;
        }
        List<CartOptionResponse> cartOptionResponseList = new ArrayList<CartOptionResponse>();

        for (CustomProductOption productCartOption : options) {
            OptionDetail optionDetail = productCartOption.getOptionDetail();
            CartOptionResponse cartOptionResponse = CartOptionResponse.of(optionDetail.getProductOption().getId(), optionDetail.getProductOption().getName(), optionDetail.getId(), optionDetail.getName(), optionDetail.getPrice(), optionDetail.getProductOption().isRequire());
            cartOptionResponseList.add(cartOptionResponse);
        }
        return cartOptionResponseList;
    }
}
