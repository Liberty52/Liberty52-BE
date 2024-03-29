package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.common.exception.external.badrequest.BadRequestException;
import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.global.adapter.s3.S3UploaderApi;
import com.liberty52.main.global.exception.external.forbidden.NotYourReviewException;
import com.liberty52.main.service.applicationservice.ReviewModifyService;
import com.liberty52.main.service.controller.dto.ReviewImagesRemoveRequestDto;
import com.liberty52.main.service.controller.dto.ReviewModifyRequestDto;
import com.liberty52.main.service.entity.Review;
import com.liberty52.main.service.entity.ReviewImage;
import com.liberty52.main.service.event.internal.ImageRemovedEvent;
import com.liberty52.main.service.event.internal.dto.ImageRemovedEventDto;
import com.liberty52.main.service.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewModifyServiceImpl implements ReviewModifyService {
    private static final String RESOURCE_NAME_REVIEW = "Review";
    private static final String PARAM_NAME_ID = "ID";
    private final ApplicationEventPublisher eventPublisher;
    private final ReviewRepository reviewRepository;
    private final S3UploaderApi s3Uploader;

    @Override
    public void modifyReviewRatingContent(String reviewerId, String reviewId, ReviewModifyRequestDto dto) {
        Review review = validAndGetReview(reviewerId, reviewId);
        review.modify(dto.getRating(), dto.getContent());
    }

    @Override
    public <T extends MultipartFile> void addReviewImages(String reviewerId, String reviewId, List<T> images) {
        if (images.size() > Review.IMAGES_MAX_COUNT || images.isEmpty())
            throw new BadRequestException(1 + " <= Size of images <= " + Review.IMAGES_MAX_COUNT);
        Review review = validAndGetReview(reviewerId, reviewId);
        addImagesInReview(review, images);
    }

    @Override
    public void removeReviewImages(String reviewerId, String reviewId, ReviewImagesRemoveRequestDto dto) {
        Review review = validAndGetReview(reviewerId, reviewId);
        List<ReviewImage> reviewImages = review.getReviewImages().stream().filter(ri -> dto.getUrls().contains(ri.getUrl())).toList();
        reviewImages.forEach(review::removeImage);
        reviewImages.forEach(ri -> eventPublisher.publishEvent(new ImageRemovedEvent(this, new ImageRemovedEventDto(ri.getUrl()))));
    }

    @Override
    public <T extends MultipartFile> void modifyReview(String reviewerId, String reviewId, ReviewModifyRequestDto dto, List<T> images) {
        Review review = validAndGetReview(reviewerId, reviewId);
        review.modify(dto.getRating(), dto.getContent());
        List<String> urls = review.getReviewImages().stream().map(ReviewImage::getUrl).toList();
        review.clearImages();
        addImagesInReview(review, images);
        urls.forEach(url -> eventPublisher.publishEvent(new ImageRemovedEvent(this, new ImageRemovedEventDto(url))));
    }

    private <T extends MultipartFile> void addImagesInReview(Review review, List<T> images) {
        if (images == null) return;
        for (MultipartFile image : images) {
            if (!review.isImageAddable()) break;
            String url = s3Uploader.upload(image);
            ReviewImage.create(review, url);
        }
    }

    private Review validAndGetReview(String reviewerId, String reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME_REVIEW, PARAM_NAME_ID, reviewId));
        if (!reviewerId.equals(review.getCustomProduct().getOrders().getAuthId()))
            throw new NotYourReviewException(reviewerId);
        return review;
    }
}
