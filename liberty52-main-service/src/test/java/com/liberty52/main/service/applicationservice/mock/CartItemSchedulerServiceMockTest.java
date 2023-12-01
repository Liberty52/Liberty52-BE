package com.liberty52.main.service.applicationservice.mock;

import com.liberty52.main.MockS3Test;
import com.liberty52.main.service.applicationservice.impl.CartItemSchedulerServiceImpl;
import com.liberty52.main.service.entity.*;
import com.liberty52.main.service.repository.CartRepository;
import com.liberty52.main.service.repository.CustomProductOptionRepository;
import com.liberty52.main.service.repository.CustomProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atMostOnce;

@ExtendWith(MockitoExtension.class)
public class CartItemSchedulerServiceMockTest extends MockS3Test {

    @Mock
    CartRepository cartRepository;

    @Mock
    CustomProductRepository customProductRepository;

    @Mock
    CustomProductOptionRepository customProductOptionRepository;

    @InjectMocks
    CartItemSchedulerServiceImpl cartItemSchedulerService;

    @Mock
    ApplicationEventPublisher eventPublisher;

    Cart cart;
    List<String> productID = new ArrayList<>();
    List<String> optionId = new ArrayList<>();
    String authId = "guest";

    @BeforeEach
    void init() {
        Product product = Product.create("Liberty 52_Frame", ProductState.ON_SALE, 100L, true,1);

        ProductOption option1 = ProductOption.create("거치 방식", true, true);
        option1.associate(product);
        OptionDetail detailEasel = OptionDetail.create("이젤 거치형", 100, true, 100);
        detailEasel.associate(option1);
        OptionDetail detailWall = OptionDetail.create("벽걸이형", 100, true, 100);
        detailWall.associate(option1);

        ProductOption option2 = ProductOption.create("기본소재", true, true);
        option2.associate(product);
        OptionDetail material = OptionDetail.create("1mm 두께 승화전사 인쇄용 알루미늄시트", 100, true, 100);
        material.associate(option2);

        ProductOption option3 = ProductOption.create("기본소재 옵션", true, true);
        option3.associate(product);
        OptionDetail materialOption1 = OptionDetail.create("유광실버", 100, true, 100);
        materialOption1.associate(option3);
        OptionDetail materialOption2 = OptionDetail.create("무광실버", 100, true, 100);
        materialOption2.associate(option3);
        OptionDetail materialOption3 = OptionDetail.create("유광백색", 100, true, 100);
        materialOption3.associate(option3);
        OptionDetail materialOption4 = OptionDetail.create("무광백색", 100, true, 100);
        materialOption4.associate(option3);

        // Add Cart & CartItems
        Cart cart = Cart.create("authId");

        CustomProduct customProduct1 = CustomProduct.create("image", 1, "authId");
        customProduct1.associateWithProduct(product);
        customProduct1.associateWithCart(cart);

        CustomProductOption customProductOption1 = CustomProductOption.create();
        customProductOption1.associate(detailEasel);
        customProductOption1.associate(customProduct1);

        CustomProductOption customProductOption2 = CustomProductOption.create();
        customProductOption2.associate(material);
        customProductOption2.associate(customProduct1);

        CustomProductOption customProductOption3 = CustomProductOption.create();
        customProductOption3.associate(materialOption2);
        customProductOption3.associate(customProduct1);

        CustomProduct customProduct2 = CustomProduct.create("image", 2, "authId");
        customProduct2.associateWithProduct(product);
        customProduct2.associateWithCart(cart);

        CustomProductOption customProductOption4 = CustomProductOption.create();
        customProductOption4.associate(detailWall);
        customProductOption4.associate(customProduct2);

        CustomProductOption customProductOption5 = CustomProductOption.create();
        customProductOption5.associate(materialOption4);
        customProductOption5.associate(customProduct2);
        this.cart = cart;
    }

    @Test
    void 스케줄러삭제() throws IOException {
        //given
        List<Cart> carts = new ArrayList<>();
        carts.add(this.cart);

        given(cartRepository.findByExpiryDateLessThan(LocalDate.now())).willReturn(carts);
        //when
        cartItemSchedulerService.deleteNonMemberCart();
        //then

        Mockito.verify(cartRepository, atMostOnce()).findByExpiryDateLessThan(any());
    }

}
