package com.liberty52.main.service.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewImage {
    @Id
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "review_id")
    private Review review;

    public static ReviewImage create(Review review, String url) {
        ReviewImage reviewImage = new ReviewImage();
        reviewImage.url = url;
        reviewImage.associate(review);
        return reviewImage;
    }

    public void associate(Review review) {
        this.review = review;
        review.addImage(this);
    }
}
