package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.controller.dto.CartModifyRequestDto;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface CartItemModifyService {
  void modifyCartItemList(String authId, List<CartModifyRequestDto> dto, MultipartFile imageFile);

  void modifyGuestCartItemList(String guestId, List<CartModifyRequestDto> dto, MultipartFile imageFile);
}
