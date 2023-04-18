package com.liberty52.product.service.applicationservice;

import com.liberty52.product.global.adapter.S3Uploader;
import com.liberty52.product.global.exception.external.CustomProductNotFoundExcpetion;
import com.liberty52.product.global.exception.external.NotYourResourceException;
import com.liberty52.product.global.exception.external.OptionDetailNotFoundException;
import com.liberty52.product.global.exception.external.OrderItemCannotModifiedException;
import com.liberty52.product.service.controller.dto.CartModifyRequestDto;
import com.liberty52.product.service.entity.CustomProduct;
import com.liberty52.product.service.entity.CustomProductOption;
import com.liberty52.product.service.entity.OptionDetail;
import com.liberty52.product.service.repository.CustomProductOptionRepository;
import com.liberty52.product.service.repository.CustomProductRepository;
import com.liberty52.product.service.repository.OptionDetailRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class CartItemModifyServiceImpl implements CartItemModifyService {

  private final S3Uploader s3Uploader;
  private final CustomProductRepository customProductRepository;
  private final OptionDetailRepository optionDetailRepository;

  private final CustomProductOptionRepository customProductOptionRepository;

  @Transactional
  @Override
  public void modifyCartItemList(String authId, List<CartModifyRequestDto> dto, MultipartFile imageFile) {
    modifyCartItem(authId, dto, imageFile);
  }

  @Override
  public void modifyGuestCartItemList(String guestId, List<CartModifyRequestDto> dto, MultipartFile imageFile) {
    modifyCartItem(guestId, dto, imageFile);
  }

  private void modifyCartItem(String ownerId, List<CartModifyRequestDto> dto, MultipartFile imageFile) {
    dto.forEach(cmrd -> {
      CustomProduct customProduct = customProductRepository.findById(cmrd.getCustomProductId())
          .orElseThrow((CustomProductNotFoundExcpetion::new));
      validCartItem(ownerId, customProduct);
      modifyOptionsDetail(cmrd, customProduct, imageFile);
    });
  }

  private void modifyOptionsDetail(CartModifyRequestDto dto, CustomProduct customProduct, MultipartFile imageFile) {
    String customPictureUrl = uploadImage(imageFile);
    if (customPictureUrl != null) {
      customProduct.modifyCustomPictureUrl(customPictureUrl);
    }
    customProduct.modifyQuantity(dto.getQuantity());

    customProductOptionRepository.deleteAll(customProduct.getOptions());
    for (String optionDetailName : dto.getOptions()) {
      CustomProductOption customProductOption = CustomProductOption.create();
      OptionDetail optionDetail = optionDetailRepository.findByName(optionDetailName)
          .orElseThrow(() -> new OptionDetailNotFoundException(optionDetailName));
      customProductOption.associate(optionDetail);
      customProductOption.associate(customProduct);
      customProductOptionRepository.save(customProductOption);
    }
  }

  private static void validCartItem(String ownerId, CustomProduct customProduct) {
    if (customProduct.isInOrder()) {
      throw new OrderItemCannotModifiedException();
    }

    if (!customProduct.getCart().getAuthId().equals(ownerId)) {
      throw new NotYourResourceException("customProduct", ownerId);
    }
  }

  private String uploadImage(MultipartFile multipartFile) {
    if (multipartFile == null) {
      return null;
    }
    return s3Uploader.upload(multipartFile);
  }

}