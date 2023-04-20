package com.liberty52.product.service.repository;

import static com.liberty52.product.service.entity.QOrders.orders;
import static com.liberty52.product.service.entity.QProduct.product;
import static com.liberty52.product.service.entity.QReply.reply;
import static com.liberty52.product.service.entity.QReview.review;
import static com.liberty52.product.service.entity.QReviewImage.reviewImage;

import com.liberty52.product.service.controller.dto.ReviewRetrieveResponse;
import com.liberty52.product.service.entity.QOrders;
import com.liberty52.product.service.entity.QProduct;
import com.liberty52.product.service.entity.QReview;
import com.liberty52.product.service.entity.Review;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ReviewQueryRepositoryImpl implements ReviewQueryRepository{

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public ReviewQueryRepositoryImpl(EntityManager em){
        this.em = em;
        this.queryFactory = new JPAQueryFactory(this.em);
    }

    public ReviewRetrieveResponse retrieveReview(String productId, String authorId, Pageable pageable){



        List<Review> reviews = queryFactory.selectFrom(review)
                .leftJoin(reply).on(reply.review.eq(review)).fetchJoin()
                .leftJoin(reviewImage).on(reviewImage.review.eq(review)).fetchJoin()
                .leftJoin(orders).on(review.order.eq(orders)).fetchJoin()
                .where(review.product.id.eq(productId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        int currentPage = pageable.getPageNumber() +1;
        int startPage = 10 * (currentPage/10) +1 ; // 1 11 21 31..

        Long total = queryFactory.select(review.count())
                .from(review)
                .where(review.product.id.eq(productId))
                .fetchOne();

        long totalLastPage = total%pageable.getPageSize() == 0 ? total / pageable.getPageSize() :
                total / pageable.getPageSize()+1;


        long lastPage = Math.min(totalLastPage,
                10L * (currentPage%10 == 0 ? currentPage/10 : currentPage / 10 + 1));
        return new ReviewRetrieveResponse(reviews, currentPage, startPage, lastPage,authorId);


    }

}
