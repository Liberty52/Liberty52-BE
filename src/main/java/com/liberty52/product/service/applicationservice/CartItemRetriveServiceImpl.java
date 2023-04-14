package com.liberty52.product.service.applicationservice;

import com.liberty52.product.global.exception.external.NotFoundAuthIdException;
import com.liberty52.product.global.exception.external.NotFoundCartProductException;
import com.liberty52.product.service.controller.dto.CartItemResponse;
import com.liberty52.product.service.controller.dto.CartOptionResponse;
import com.liberty52.product.service.entity.*;
import com.liberty52.product.service.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemRetriveServiceImpl implements CartItemRetriveService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final OptionDetailRepository optionDetailRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomProductOptionRepository productCartOptionRepository;
    private final CartRepository cartRepository;

    @Override
    public List<CartItemResponse> retriveCartItem(String authId) {
        Cart cart = cartRepository.findByAuthId(authId).orElseThrow(()-> new NotFoundAuthIdException());

        if (cart.getCustomProducts().size() == 0){
            throw new NotFoundCartProductException();
        }

        List<CartItemResponse> cartItemResponseList = new ArrayList<CartItemResponse>();
        List<CustomProduct> cartItemList = cart.getCustomProducts();

        for(CustomProduct cartItem:cartItemList){
            Product product = cartItem.getProduct();
            CartItemResponse cartItemResponse = CartItemResponse.of(cartItem.getId(), product.getName(), cartItem.getUserCustomPictureUrl(), product.getPrice(), cartItem.getQuantity(), getCartOptionList(cartItem.getOptions()));
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
            CartOptionResponse cartOptionResponse = CartOptionResponse.of(optionDetail.getProductOption().getName(), optionDetail.getName(), optionDetail.getPrice(), optionDetail.getProductOption().isRequire());
            cartOptionResponseList.add(cartOptionResponse);
        }
        return cartOptionResponseList;
    }
}
