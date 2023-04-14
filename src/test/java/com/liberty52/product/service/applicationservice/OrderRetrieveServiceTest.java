package com.liberty52.product.service.applicationservice;

import static com.liberty52.product.service.utils.MockConstants.MOCK_AUTH_ID;
import static com.liberty52.product.service.utils.MockConstants.MOCK_RECEIVER_EMAIL;
import static com.liberty52.product.service.utils.MockConstants.MOCK_RECEIVER_NAME;
import static com.liberty52.product.service.utils.MockConstants.MOCK_RECEIVER_PHONE_NUMBER;
import static com.liberty52.product.service.utils.TestInitiator.initDataForTestingOrder;
import static org.assertj.core.api.Assertions.assertThat;

import com.liberty52.product.service.controller.dto.OrdersRetrieveResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderRetrieveServiceTest {

    @Autowired
    OrderRetrieveService orderRetrieveService;


    @Autowired
    EntityManagerFactory emf;

    @BeforeEach
    void beforeEach(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        initDataForTestingOrder(em);
        tx.commit();
    }
    
    
    @Test
    void retrieveOrdersTest () throws Exception{
        //given
        //when
        List<OrdersRetrieveResponse> responses = orderRetrieveService.retrieveOrders(
                MOCK_AUTH_ID);
        //then
        assertThat(responses.size()).isSameAs(1);
        assertThat(responses.get(0).getOrderDate()).isEqualTo(LocalDate.now().toString());
        assertThat(responses.get(0).getReceiverEmail()).isEqualTo(MOCK_RECEIVER_EMAIL);
        assertThat(responses.get(0).getReceiverPhoneNumber()).isEqualTo(MOCK_RECEIVER_PHONE_NUMBER);
        assertThat(responses.get(0).getReceiverName()).isEqualTo(MOCK_RECEIVER_NAME);
        
    
    }



}