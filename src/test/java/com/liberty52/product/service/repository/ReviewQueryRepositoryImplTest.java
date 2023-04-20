package com.liberty52.product.service.repository;

import static com.liberty52.product.service.entity.QOrders.orders;
import static com.liberty52.product.service.entity.QReply.reply;
import static com.liberty52.product.service.entity.QReview.review;
import static com.liberty52.product.service.entity.QReviewImage.reviewImage;
import static org.junit.jupiter.api.Assertions.*;

import com.liberty52.product.global.config.DBInitConfig;
import com.liberty52.product.global.config.DBInitConfig.DBInitService;
import com.liberty52.product.service.entity.Orders;
import com.liberty52.product.service.entity.Product;
import com.liberty52.product.service.entity.QOrders;
import com.liberty52.product.service.entity.QProduct;
import com.liberty52.product.service.entity.QReply;
import com.liberty52.product.service.entity.QReview;
import com.liberty52.product.service.entity.QReviewImage;
import com.liberty52.product.service.entity.Review;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ReviewQueryRepositoryImplTest {


    @Autowired
    EntityManager em;

    @Autowired
    DBInitConfig config;

    JPAQueryFactory qf;
    Orders order;
    Product product;
    @BeforeEach
    void beforeEach(){
        qf = new JPAQueryFactory(em);
        order = DBInitService.getOrder();
        product = DBInitService.getProduct();
    }


    @Test
    void reviewPagingTest () throws Exception{
        final String orderId = "orderId";
        final String productId = "productId";

//        order

        PageRequest request = PageRequest.of(0, 5);
        //given
        List<Review> fetch = qf.selectFrom(review)
                .leftJoin(reply).on(reply.review.eq(review)).fetchJoin()
                .leftJoin(reviewImage).on(reviewImage.review.eq(review)).fetchJoin()
                .where(review.product.id.eq(product.getId()))
                .offset(request.getOffset())
                .limit(request.getPageSize())
                .fetch();

        Long total = qf.select(review.count())
                .from(review)
                .where(review.product.id.eq(product.getId()))
                .fetchOne();

        PageImpl<Review> page = new PageImpl<>(fetch, request, total);

        System.out.println(page.getPageable());
        System.out.println(page.getTotalPages());
        System.out.println(page.getTotalElements());
//        System.out.println(page.getPageable());
        System.out.println(page.getContent());

        //when

        //then


    }

//    private BooleanExpression
}