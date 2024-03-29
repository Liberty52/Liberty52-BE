package com.liberty52.main.service.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liberty52.main.service.entity.Review;
import com.liberty52.main.service.entity.ReviewImage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
public class AdminReviewDetailResponse {

    private ReviewContent content;

    @JsonIgnore
    private Set<String> authorIds = new HashSet<>();

    public AdminReviewDetailResponse(Review review) {
        String orderAuthId = review.getCustomProduct().getOrders().getAuthId();
        content = new ReviewContent(review.getId(), review.getRating(), review.getContent(),
                review.getReviewImages().stream().map(
                        ReviewImage::getUrl).toList(), review.getCreatedAt().toLocalDate(), orderAuthId,
                review.getReplies().stream().map(
                        rp -> new ReplyContent(rp.getAuthId(), rp.getContent(), rp.getId(),
                                rp.getCreatedAt().toLocalDate())).toList());
        authorIds.add(orderAuthId);
        content.replies.forEach(rp -> authorIds.add(rp.authorId));
    }

    public void setReviewAuthor(Map<String, AuthClientDataResponse> reviewAuthorId) {
        content.authorName = reviewAuthorId.get(content.authorId).getAuthorName();
        content.setReplyAuthor(reviewAuthorId);
    }

    @Data
    public class ReviewContent {

        public String authorName;
        private String reviewId;
        private Integer rating;
        private String content;
        private List<String> imageUrls;
        private String authorId;
        private LocalDate reviewCreatedAt;
        private List<ReplyContent> replies;

        public ReviewContent(String reviewId, Integer rating, String content, List<String> imageUrls,
                             LocalDate reviewCreatedAt,
                             String authorId, List<ReplyContent> replies) {
            this.reviewId = reviewId;
            this.rating = rating;
            this.content = content;
            this.imageUrls = imageUrls;
            this.authorId = authorId;
            this.reviewCreatedAt = reviewCreatedAt;
            this.replies = replies;
        }

        public void setReplyAuthor(Map<String, AuthClientDataResponse> map) {
            replies.forEach(r -> {
                AuthClientDataResponse data = map.get(r.authorId);
                r.authorName = data.getAuthorName();
            });
        }
    }

    @Data
    public class ReplyContent {

        private String authorId;
        private String authorName;
        private String content;
        private String replyId;
        private LocalDate replyCreatedAt;

        public ReplyContent(String authorId, String content, String replyId, LocalDate replyCreatedAt) {
            this.authorId = authorId;
            this.content = content;
            this.replyId = replyId;
            this.replyCreatedAt = replyCreatedAt;
        }

    }
}
