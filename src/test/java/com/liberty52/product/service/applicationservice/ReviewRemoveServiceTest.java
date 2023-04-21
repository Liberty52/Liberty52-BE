package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ReviewRemoveServiceTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewRemoveService reviewRemoveService;

    @Test
    void 리뷰삭제() {

    }
}
