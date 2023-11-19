package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.main.global.adapter.s3.S3UploaderApi;
import com.liberty52.main.global.exception.external.notfound.OptionDetailNotFoundByNameException;
import com.liberty52.main.global.exception.external.notfound.ProductNotFoundByNameException;
import com.liberty52.main.service.applicationservice.CartItemCreateService;
import com.liberty52.main.service.controller.dto.CartItemRequest;
import com.liberty52.main.service.entity.*;
import com.liberty52.main.service.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
@Transactional
public class CartItemCreateServiceImpl implements CartItemCreateService {

    private final S3UploaderApi s3Uploader;
    private final ProductRepository productRepository;
    private final OptionDetailRepository optionDetailRepository;
    private final CustomProductRepository customProductRepository;
    private final CartRepository cartRepository;
    private final CustomProductOptionRepository customProductOptionRepository;


    @Override
    public void createAuthCartItem(String authId, MultipartFile imageFile, CartItemRequest dto) {
        Cart cart = cartRepository.findByAuthId(authId).orElseGet(() -> createCart(authId));
        createCartItem(cart, authId, imageFile, dto);
    }

    private Cart createCart(String authId) {
        Cart cart = Cart.create(authId);
        cart = cartRepository.save(cart);
        return cart;
    }

    private void createCartItem(Cart cart, String authId, MultipartFile imageFile, CartItemRequest dto) {
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new ProductNotFoundByNameException(dto.getProductId())); //예외처리 해야함

        CustomProduct customProduct = CustomProduct.createCartItem(authId, dto.getQuantity(), s3Uploader.upload(imageFile));
        customProduct.associateWithProduct(product);
        customProduct.associateWithCart(cart);
        customProductRepository.save(customProduct);

        for (String optionDetailId : dto.getOptionDetailIds()) {
            CustomProductOption customProductOption = CustomProductOption.create();
            OptionDetail optionDetail = optionDetailRepository.findById(optionDetailId).orElseThrow(() -> new OptionDetailNotFoundByNameException(optionDetailId));

            customProductOption.associate(optionDetail);
            customProductOption.associate(customProduct);
            customProductOptionRepository.save(customProductOption);
        }
    }

    @Override
    public void createGuestCartItem(String guestId, MultipartFile imageFile, CartItemRequest dto) {
        LocalDate today = LocalDate.now();
        Cart cart = cartRepository.findByAuthIdAndExpiryDateGreaterThanEqual(guestId, today).orElseGet(() -> createGuestCart(guestId));
        cart.updateExpiryDate(today.plusDays(7));
        createCartItem(cart, guestId, imageFile, dto);
    }

    private Cart createGuestCart(String guestId) {
        Cart cart = Cart.create(guestId);
        cart = cartRepository.save(cart);
        return cart;
    }
}
