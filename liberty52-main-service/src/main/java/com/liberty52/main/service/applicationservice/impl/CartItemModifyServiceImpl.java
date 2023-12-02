package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.main.global.adapter.s3.S3UploaderApi;
import com.liberty52.main.global.exception.external.badrequest.CartItemRequiredButOrderItemFoundException;
import com.liberty52.main.global.exception.external.forbidden.NotYourCartItemException;
import com.liberty52.main.global.exception.external.notfound.CustomProductNotFoundByIdException;
import com.liberty52.main.global.exception.external.notfound.OptionDetailNotFoundByNameException;
import com.liberty52.main.service.applicationservice.CartItemModifyService;
import com.liberty52.main.service.controller.dto.CartModifyRequestDto;
import com.liberty52.main.service.controller.dto.CartModifyWithLicenseRequestDto;
import com.liberty52.main.service.entity.CustomProduct;
import com.liberty52.main.service.entity.CustomProductOption;
import com.liberty52.main.service.entity.OptionDetail;
import com.liberty52.main.service.entity.license.CustomLicenseOption;
import com.liberty52.main.service.entity.license.LicenseOptionDetail;
import com.liberty52.main.service.event.internal.ImageRemovedEvent;
import com.liberty52.main.service.event.internal.dto.ImageRemovedEventDto;
import com.liberty52.main.service.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CartItemModifyServiceImpl implements CartItemModifyService {

    private final S3UploaderApi s3Uploader;
    private final ApplicationEventPublisher eventPublisher;
    private final CustomProductRepository customProductRepository;
    private final OptionDetailRepository optionDetailRepository;

    private final CustomProductOptionRepository customProductOptionRepository;
    private final LicenseOptionDetailRepository licenseOptionDetailRepository;
    private final CustomLicenseOptionRepository customLicenseOptionRepository;


    @Override
    public void modifyUserCartItem(String authId, CartModifyRequestDto dto, MultipartFile imageFile, String customProductId) {
        modifyCartItem(authId,dto,imageFile,customProductId);
    }
    @Override
    public void modifyGuestCartItem(String guestId, CartModifyRequestDto dto, MultipartFile imageFile, String customProductId) {
        modifyCartItem(guestId,dto,imageFile,customProductId);
    }

    @Override
    public void modifyUserCartItemWihLicense(String authId, CartModifyWithLicenseRequestDto dto, String customProductId) {
        modifyCartItemWithLicence(authId,dto,customProductId);
    }

    @Override
    public void modifyGuestCartItemWithLicense(String guestId, CartModifyWithLicenseRequestDto dto, String customProductId) {
            modifyCartItemWithLicence(guestId,dto,customProductId);

    }

    @Override
    public void modifyUserCartItemImage(String authId, MultipartFile imageFile, String customProductId) {
        CustomProduct customProduct = customProductRepository.findById(customProductId).orElseThrow(() -> new CustomProductNotFoundByIdException(customProductId));
        modifyImage(customProduct, imageFile);

    }

    @Override
    public void modifyGuestCartItemImage(String guestId, MultipartFile imageFile, String customProductId) {
        CustomProduct customProduct = customProductRepository.findById(customProductId).orElseThrow(() -> new CustomProductNotFoundByIdException(customProductId));
        modifyImage(customProduct, imageFile);
    }

    private void modifyCartItemWithLicence(String ownerId, CartModifyWithLicenseRequestDto dto, String customProductId) {
        CustomProduct customProduct = customProductRepository.findById(customProductId).orElseThrow(() -> new CustomProductNotFoundByIdException(customProductId));
        validCartItem(ownerId, customProduct);
        modifyLicenseOption(dto.getLicenseId(), customProduct);
    }

    private void modifyLicenseOption(String licenseId, CustomProduct customProduct) {
        if(licenseId != customProduct.getCustomLicenseOption().getId()) {
            LicenseOptionDetail licenseOptionDetail = licenseOptionDetailRepository.findById(licenseId).orElseThrow(() -> new OptionDetailNotFoundByNameException(licenseId));
            CustomLicenseOption customLicenseOption = CustomLicenseOption.create();
            customLicenseOption.associate(licenseOptionDetail);
            customLicenseOption.associate(customProduct);
            customLicenseOptionRepository.save(customLicenseOption);
        }
    }

    private void modifyCartItem(String ownerId, CartModifyRequestDto dto, MultipartFile imageFile, String customProductId) {
        CustomProduct customProduct = customProductRepository.findById(customProductId).orElseThrow(() -> new CustomProductNotFoundByIdException(customProductId));
        validCartItem(ownerId, customProduct);
        customProduct.modifyQuantity(dto.getQuantity());
        modifyImage(customProduct, imageFile);
        modifyOptionsDetail(dto.getOptionDetailIds(), customProduct);
        customProductRepository.save(customProduct);
    }

    private void modifyImage(CustomProduct customProduct, MultipartFile imageFile) {
        if (imageFile != null){
            String url = customProduct.getUserCustomPictureUrl();
            String customPictureUrl = s3Uploader.upload(imageFile);
            customProduct.modifyCustomPictureUrl(customPictureUrl);
            eventPublisher.publishEvent(new ImageRemovedEvent(this, new ImageRemovedEventDto(url)));
        }
    }

    private void validCartItem(String authId, CustomProduct customProduct) {
        if(customProduct.isInOrder()){
            throw new CartItemRequiredButOrderItemFoundException(customProduct.getId());
        }

        if(!customProduct.getCart().getAuthId().equals(authId)){
            throw new NotYourCartItemException(authId);
        }
    }

    private void modifyOptionsDetail(List<String> optionDetailIds, CustomProduct customProduct) {

        if (!optionDetailIds.isEmpty()){
            customProductOptionRepository.deleteAll(customProduct.getOptions());
            for (String optionDetailId : optionDetailIds){
                CustomProductOption customProductOption = CustomProductOption.create();
                OptionDetail optionDetail = optionDetailRepository.findById(optionDetailId)
                        .orElseThrow(() -> new CustomProductNotFoundByIdException(optionDetailId));
                customProductOption.associate(optionDetail);
                customProductOption.associate(customProduct);
                customProductOptionRepository.save(customProductOption);
            }
        }
    }
}
