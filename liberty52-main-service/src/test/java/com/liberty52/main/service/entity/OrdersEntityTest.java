package com.liberty52.main.service.entity;

import com.liberty52.main.service.utils.MockFactory;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrdersEntityTest {

    @Test
    void calculateTotalValueAndSet_givenOneCustomProduct_success() {
        // given
        var authId = "users";
        var product = givenProduct(10000L, 1000);
        var customProduct = givenCustomProduct(
                authId,
                product,
                product.getProductOptions().get(0).getOptionDetails().get(0), // 1000
                product.getProductOptions().get(1).getOptionDetails().get(1) // 10000
        );

        var order = givenOrder(authId, customProduct);

        // when
        order.calculateTotalValueAndSet();

        // then
        var expected = 10000 + 1000 + 10000 + 1000;
        assertEquals(expected, order.getAmount());
    }

    @Test
    void calculateTotalValueAndSet_givenTwoCustomProductOfAnotherProduct_success() {
        // given
        var authId = "user";
        var product_1 = givenProduct(10000L, 100);
        var product_2 = givenProduct(100_000L, 10000);

        var customProducts = List.of(
                givenCustomProduct(
                        authId,
                        product_1,
                        product_1.getProductOptions().get(0).getOptionDetails().get(1), // 100
                        product_1.getProductOptions().get(1).getOptionDetails().get(0) // 1000
                ),
                givenCustomProduct(
                        authId,
                        product_2,
                        product_2.getProductOptions().get(0).getOptionDetails().get(0), // 1000
                        product_2.getProductOptions().get(1).getOptionDetails().get(1) // 10000
                )
        );

        var order = givenOrder(authId, customProducts);

        // when
        order.calculateTotalValueAndSet();

        // then
        var expected = 10000 + 100 + 1000 + 100 + 100_000 + 1000 + 10000 + 10000;
        assertEquals(expected, order.getAmount());
    }

    private Orders givenOrder(
            String authId,
            List<CustomProduct> customProducts
    ) {
        var order = MockFactory.createOrder(authId);
        customProducts.forEach(it -> it.associateWithOrder(order));
        return order;
    }

    private Orders givenOrder(
            String authId,
            CustomProduct customProduct
    ) {
        return givenOrder(authId, List.of(customProduct));
    }

    private CustomProduct givenCustomProduct(
            String authId,
            Product product,
            OptionDetail... selectedOptions
    ) {
        var customProduct = MockFactory.createCustomProduct("image", 1, authId, product);

        Arrays.stream(selectedOptions).forEach(it ->
                MockFactory.createCustomProductOption(customProduct, it));

        return customProduct;
    }

    private Product givenProduct(
            Long productPrice,
            Integer deliveryFee
    ) {
        var product = MockFactory.createProduct("product", productPrice);

        var productOption1 = MockFactory.createProductOption("product-option-1", true);
        var optionDetail1 = MockFactory.createOptionDetail("detail-1", 1000);
        var optionDetail2 = MockFactory.createOptionDetail("detail-2", 100);
        optionDetail1.associate(productOption1);
        optionDetail2.associate(productOption1);

        var productOption2 = MockFactory.createProductOption("product-option-2", true);
        var optionDetail3 = MockFactory.createOptionDetail("detail-3", 1000);
        var optionDetail4 = MockFactory.createOptionDetail("detail-4", 10000);
        optionDetail3.associate(productOption2);
        optionDetail4.associate(productOption2);

        product.addOption(productOption1);
        product.addOption(productOption2);

        MockFactory.createProductDeliveryOption("cj", deliveryFee, product);

        return product;
    }
}
