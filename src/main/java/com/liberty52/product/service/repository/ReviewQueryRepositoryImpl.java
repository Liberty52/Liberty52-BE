package com.liberty52.product.service.repository;

import static com.liberty52.product.service.entity.QOrders.orders;
import static com.liberty52.product.service.entity.QReply.reply;
import static com.liberty52.product.service.entity.QReview.review;
import static com.liberty52.product.service.entity.QReviewImage.reviewImage;

import com.liberty52.product.service.controller.dto.ReviewRetrieveResponse;
import com.liberty52.product.service.entity.Review;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ReviewQueryRepositoryImpl implements ReviewQueryRepository{

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    private final String currentPage = "currentPage";
    private final String startPage = "startPage";
    private final String lastPage = "lastPage";

    public ReviewQueryRepositoryImpl(EntityManager em){
        this.em = em;
        this.queryFactory = new JPAQueryFactory(this.em);
    }

    public ReviewRetrieveResponse retrieveReview(String productId, String authorId, Pageable pageable){

        List<Review> reviews = fetchReviews(productId, pageable);

        if(reviews.isEmpty())
            return noReviewExistCase(authorId, reviews); // 리뷰가 0인 경우에 리턴하는 케이스

        Map<String, Long> pageInfo = getPageInfo(pageable, productId);

        return new ReviewRetrieveResponse(reviews,
                pageInfo.get(startPage),
                pageInfo.get(currentPage),
                pageInfo.get(lastPage)
                ,authorId);


    }

    private List<Review> fetchReviews(String productId, Pageable pageable) {
        return queryFactory.selectFrom(review)
                .leftJoin(reply).on(reply.review.eq(review)).fetchJoin()
                .leftJoin(reviewImage).on(reviewImage.review.eq(review)).fetchJoin()
                .leftJoin(orders).on(review.order.eq(orders)).fetchJoin()
                .where(review.product.id.eq(productId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
    private Long getTotalCount(String productId) {
        return queryFactory.select(review.count())
                .from(review)
                .where(review.product.id.eq(productId))
                .fetchOne();
    }

    private Map<String,Long> getPageInfo(Pageable pageable, String productId){
        long currentPage = pageable.getPageNumber() +1;
        long startPage = 10 * (currentPage/10) +1 ; // 1 11 21 31..
        Long total = getTotalCount(productId);
        long totalLastPage = total%pageable.getPageSize() == 0 ? total / pageable.getPageSize() :
                total / pageable.getPageSize()+1;
        long lastPage = Math.min(totalLastPage,
                10L * (currentPage%10 == 0 ? currentPage/10 : currentPage / 10 + 1));
        Map<String,Long> returnMap = new HashMap<>();
        returnMap.put(this.startPage,startPage);
        returnMap.put(this.currentPage,currentPage);
        returnMap.put(this.lastPage,lastPage);
        return returnMap;
    }

    private ReviewRetrieveResponse noReviewExistCase(String authorId,
            List<Review> reviews) {
        return new ReviewRetrieveResponse(reviews, 0, 0, 0, authorId);
    }

}
