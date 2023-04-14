package com.liberty52.product.service.repository;

import static com.liberty52.product.service.utils.MockConstants.MOCK_ADDRESS;
import static com.liberty52.product.service.utils.MockConstants.MOCK_AUTH_ID;
import static com.liberty52.product.service.utils.MockConstants.MOCK_RECEIVER_EMAIL;
import static com.liberty52.product.service.utils.MockConstants.MOCK_RECEIVER_NAME;
import static com.liberty52.product.service.utils.MockConstants.MOCK_RECEIVER_PHONE_NUMBER;
import static com.liberty52.product.service.utils.MockFactory.*;
import static com.liberty52.product.service.utils.TestInitiator.initDataForTestingOrder;
import static org.assertj.core.api.Assertions.*;

import com.liberty52.product.service.controller.dto.OrdersRetrieveResponse;
import com.liberty52.product.service.entity.CustomProduct;
import com.liberty52.product.service.entity.OrderDestination;
import com.liberty52.product.service.entity.Orders;
import com.liberty52.product.service.entity.Product;
import com.liberty52.product.service.entity.ProductState;
import com.liberty52.product.service.utils.TestInitiator;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class OrderQueryDslRepositoryImplTest {


    @Autowired
    EntityManager em;

    OrderQueryDslRepositoryImpl orderQueryDslRepositoryImpl;


    @BeforeEach
    void beforeEach(){
        orderQueryDslRepositoryImpl = new OrderQueryDslRepositoryImpl(em);
        initDataForTestingOrder(em);
    }



    @Test
    void retrieveOrderTest  () throws Exception{

        //given
        List<OrdersRetrieveResponse> responses = orderQueryDslRepositoryImpl.retrieveOrders(
                MOCK_AUTH_ID);
        //when

        //then
//
        assertThat(responses.size()).isSameAs(1);
        assertThat(responses.get(0).getOrderDate()).isEqualTo(LocalDate.now().toString());
        assertThat(responses.get(0).getReceiverEmail()).isEqualTo(MOCK_RECEIVER_EMAIL);
        assertThat(responses.get(0).getReceiverPhoneNumber()).isEqualTo(MOCK_RECEIVER_PHONE_NUMBER);
        assertThat(responses.get(0).getReceiverName()).isEqualTo(MOCK_RECEIVER_NAME);


    }
}