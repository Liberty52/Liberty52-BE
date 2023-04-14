package com.liberty52.product.service.utils;

import static com.liberty52.product.service.utils.MockConstants.MOCK_ADDRESS;
import static com.liberty52.product.service.utils.MockConstants.MOCK_AUTH_ID;
import static com.liberty52.product.service.utils.MockConstants.MOCK_RECEIVER_EMAIL;
import static com.liberty52.product.service.utils.MockConstants.MOCK_RECEIVER_NAME;
import static com.liberty52.product.service.utils.MockConstants.MOCK_RECEIVER_PHONE_NUMBER;
import static com.liberty52.product.service.utils.MockFactory.createProduct;

import com.liberty52.product.service.entity.CustomProduct;
import com.liberty52.product.service.entity.OrderDestination;
import com.liberty52.product.service.entity.Orders;
import com.liberty52.product.service.entity.Product;
import com.liberty52.product.service.entity.ProductState;
import jakarta.persistence.EntityManager;
import java.util.List;

public class TestInitiator {

    public static void initDataForTestingOrder(EntityManager em) {
        Product product = createProduct("PRODUCT", ProductState.ON_SAIL, 10000L);
        em.persist(product);
        CustomProduct customProduct = CustomProduct.create("UUURRRLLL", 2, MOCK_AUTH_ID);
        customProduct.associateWithProduct(product);

        em.persist(customProduct);

        OrderDestination destination = OrderDestination.create(MOCK_RECEIVER_NAME,
                MOCK_RECEIVER_EMAIL
                , MOCK_RECEIVER_PHONE_NUMBER, MOCK_ADDRESS, MOCK_ADDRESS, MOCK_ADDRESS);
        Orders order = Orders.create(MOCK_AUTH_ID,0,destination);
        order.associateWithCustomProduct(List.of(customProduct));
        em.persist(order);
        em.flush();
    }

}
