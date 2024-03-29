package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.global.exception.external.forbidden.NotYourCartItemException;
import com.liberty52.main.global.exception.external.forbidden.UnRemovableResourceException;
import com.liberty52.main.service.applicationservice.CartItemRemoveService;
import com.liberty52.main.service.controller.dto.CartItemListRemoveRequestDto;
import com.liberty52.main.service.entity.CustomProduct;
import com.liberty52.main.service.event.internal.ImageRemovedEvent;
import com.liberty52.main.service.event.internal.dto.ImageRemovedEventDto;
import com.liberty52.main.service.repository.CartItemRepository;
import com.liberty52.main.service.repository.CustomProductOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartItemRemoveServiceImpl implements CartItemRemoveService {
    private static final String RESOURCE_NAME_CART_ITEM = "CartItem(CustomProduct in Cart)";
    private static final String PARAM_NAME_ID = "ID";
    private static final String RESOURCE_NAME_ORDER_ITEM = "OrderItem(CustomProduct in Order)";
    private final ApplicationEventPublisher eventPublisher;
    private final CartItemRepository cartItemRepository;
    private final CustomProductOptionRepository productCartOptionRepository;

    @Override
    public void removeCartItem(String authId, String cartItemId) {
        CustomProduct cartItem = validAndGetCartItem(authId, cartItemId);
        productCartOptionRepository.deleteAll(cartItem.getOptions());
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void removeCartItemList(String authId, CartItemListRemoveRequestDto dto) {
        removeCartItems(authId, dto.getIds());
    }

    @Override
    public void removeGuestCartItemList(String guestId, CartItemListRemoveRequestDto dto) {
        removeCartItems(guestId, dto.getIds());
    }

    @Override
    public void removeGuestCartItem(String guestId, String cartItemId) {
        removeCartItem(guestId, cartItemId);
    }

    private CustomProduct validAndGetCartItem(String authId, String cartItemId) {
        CustomProduct cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME_CART_ITEM, PARAM_NAME_ID, cartItemId));
        validCartItem(authId, cartItem);
        return cartItem;
    }

    private void validCartItem(String authId, CustomProduct cartItem) {
        if (!authId.equals(cartItem.getAuthId()))
            throw new NotYourCartItemException(authId);
        if (cartItem.isInOrder())
            throw new UnRemovableResourceException(RESOURCE_NAME_ORDER_ITEM, cartItem.getId());
    }

    private void removeCartItems(String ownerId, List<String> customProductIds) {
        List<String> urlsWillBeDeleted = new ArrayList<>();
        customProductIds.forEach(id -> {
            CustomProduct customProduct = validAndGetCartItem(ownerId, id);
            productCartOptionRepository.deleteAll(customProduct.getOptions());
            cartItemRepository.delete(customProduct);
            urlsWillBeDeleted.add(customProduct.getUserCustomPictureUrl());
        });
        urlsWillBeDeleted.forEach(url -> eventPublisher.publishEvent(new ImageRemovedEvent(this, new ImageRemovedEventDto(url))));
    }
}
