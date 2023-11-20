package com.liberty52.main.service.repository;

import com.liberty52.main.service.entity.OrderDestination;
import com.liberty52.main.service.entity.Orders;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class OrdersTest {

    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    EntityManager em;

    @Test
    void ordersAssociationWithOrderDestination() throws Exception {
        //given
        OrderDestination destination = OrderDestination.create("김", "kghdasd@naver.com", "", "", "", "");
        Orders orders = Orders.create("", destination);
        String id = orders.getId();
        ordersRepository.save(orders);
        em.flush();
        //when
        Orders orders1 = ordersRepository.findById(id).get();

        //then
        Assertions.assertThat(orders1.getOrderDestination().getReceiverName()).isEqualTo("김");


    }

}
