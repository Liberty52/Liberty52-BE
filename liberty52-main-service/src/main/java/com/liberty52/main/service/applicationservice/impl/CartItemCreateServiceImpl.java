package com.liberty52.main.service.applicationservice.impl;


import com.liberty52.main.global.adapter.s3.S3UploaderApi;
import com.liberty52.main.global.exception.external.notfound.OptionDetailNotFoundByNameException;
import com.liberty52.main.global.exception.external.notfound.ProductNotFoundByNameException;
import com.liberty52.main.service.applicationservice.CartItemCreateService;
import com.liberty52.main.service.controller.dto.CartItemRequest;
import com.liberty52.main.service.controller.dto.CartItemRequestWithLicense;
import com.liberty52.main.service.entity.*;
import com.liberty52.main.service.entity.license.CustomLicenseOption;
import com.liberty52.main.service.entity.license.LicenseOptionDetail;
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
    private final LicenseOptionDetailRepository licenseOptionDetailRepository;
    private final CustomLicenseOptionRepository customLicenseOptionRepository;


    @Override
    public void createAuthCartItem(String authId, MultipartFile imageFile, CartItemRequest dto) {
        Cart cart = cartRepository.findByAuthId(authId).orElseGet(() -> createCart(authId));
        CustomProduct customProduct = createCustomProduct(cart, dto, authId, imageFile);
        createCartItem(customProduct, dto.getOptionDetailIds());
    }

    private CustomProduct createCustomProduct(Cart cart, CartItemRequest dto, String authId, MultipartFile imageFile) {
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new ProductNotFoundByNameException(dto.getProductId())); //예외처리 해야함
        CustomProduct customProduct = CustomProduct.createCartItem(authId, dto.getQuantity(), s3Uploader.upload(imageFile));
        customProduct.associateWithProduct(product);
        customProduct.associateWithCart(cart);
        customProductRepository.save(customProduct);
        return customProduct;
    }

    @Override
    public void createAuthCartItemWithLicense(String authId, CartItemRequestWithLicense dto) {
        Cart cart = cartRepository.findByAuthId(authId).orElseGet(() -> createCart(authId));
        CustomProduct customProduct = createLicenseProduct(cart, dto, authId);
        createCartItem(customProduct, dto.getOptionDetailIds());
    }

    private CustomProduct createLicenseProduct(Cart cart, CartItemRequestWithLicense dto, String authId) {
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new ProductNotFoundByNameException(dto.getProductId())); //예외처리 해야함
        CustomProduct customProduct = CustomProduct.createCartItem(authId, dto.getQuantity(), "");
        customProduct.associateWithProduct(product);
        customProduct.associateWithCart(cart);
        customProductRepository.save(customProduct);

        LicenseOptionDetail licenseOptionDetail = licenseOptionDetailRepository.findById(dto.getLicenseOptionId()).orElseThrow(() -> new OptionDetailNotFoundByNameException(dto.getLicenseOptionId()));
        CustomLicenseOption customLicenseOption = CustomLicenseOption.create();
        customLicenseOption.associate(licenseOptionDetail);
        customLicenseOption.associate(customProduct);
        customLicenseOptionRepository.save(customLicenseOption);
        customProductRepository.save(customProduct);
        return customProduct;
    }

    private Cart createCart(String authId) {
        Cart cart = Cart.create(authId);
        cart = cartRepository.save(cart);
        return cart;
    }

    private void createCartItem(CustomProduct customProduct, String[] optionDetailIds) {
        for (String optionDetailId :optionDetailIds){
            CustomProductOption customProductOption = CustomProductOption.create();
            OptionDetail optionDetail = optionDetailRepository.findById(optionDetailId).orElseThrow(() -> new OptionDetailNotFoundByNameException(optionDetailId));

            customProductOption.associate(optionDetail);
            customProductOption.associate(customProduct);
            customProductOptionRepository.save(customProductOption);
        }

    }

    @Override
    public void createGuestCartItemWithLicense(String guestId, CartItemRequestWithLicense dto) {
        LocalDate today = LocalDate.now();
        Cart cart = cartRepository.findByAuthIdAndExpiryDateGreaterThanEqual(guestId, today).orElseGet(() -> createGuestCart(guestId));
        cart.updateExpiryDate(today.plusDays(7));
        CustomProduct customProduct = createLicenseProduct(cart, dto, guestId);
        createCartItem(customProduct, dto.getOptionDetailIds());
    }

    @Override
    public void createGuestCartItem(String guestId, MultipartFile imageFile, CartItemRequest dto) {
        LocalDate today = LocalDate.now();
        Cart cart = cartRepository.findByAuthIdAndExpiryDateGreaterThanEqual(guestId, today).orElseGet(() -> createGuestCart(guestId));
        cart.updateExpiryDate(today.plusDays(7));
        CustomProduct customProduct = createCustomProduct(cart, dto, guestId, imageFile);
        createCartItem(customProduct, dto.getOptionDetailIds());
    }

    private Cart createGuestCart(String guestId) {
        Cart cart = Cart.create(guestId);
        cart = cartRepository.save(cart);
        return cart;
    }
}
