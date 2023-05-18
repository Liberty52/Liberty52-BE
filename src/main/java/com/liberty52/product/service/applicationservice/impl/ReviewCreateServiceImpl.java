package com.liberty52.product.service.applicationservice.impl;

import com.liberty52.product.global.adapter.s3.S3UploaderApi;
import com.liberty52.product.global.exception.external.badrequest.BadRequestException;
import com.liberty52.product.global.exception.external.badrequest.ReviewAlreadyExistByOrderException;
import com.liberty52.product.global.exception.external.forbidden.NotYourOrderException;
import com.liberty52.product.global.exception.external.notfound.OrderNotFoundByIdException;
import com.liberty52.product.global.exception.external.notfound.ProductNotFoundByNameException;
import com.liberty52.product.service.applicationservice.ReviewCreateService;
import com.liberty52.product.service.controller.dto.ReviewCreateRequestDto;
import com.liberty52.product.service.entity.*;
import com.liberty52.product.service.repository.CustomProductRepository;
import com.liberty52.product.service.repository.OrdersRepository;
import com.liberty52.product.service.repository.ProductRepository;
import com.liberty52.product.service.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewCreateServiceImpl implements ReviewCreateService {

  private final ReviewRepository reviewRepository;
  private final ProductRepository productRepository;
  private final OrdersRepository ordersRepository;
  private final CustomProductRepository customProductRepository;
  private final S3UploaderApi s3Uploader;

  @Override
  public void createReview(String reviewerId, ReviewCreateRequestDto dto, List<MultipartFile> images) {
    CustomProduct product = customProductRepository.findByProductName(dto.getProductName())
        .orElseThrow(() -> new ProductNotFoundByNameException(dto.getProductName()));
    //TODO API 수정 - ProductName -> CustomProductId

    Orders order = ordersRepository.findById(dto.getOrderId())
        .orElseThrow(() -> new OrderNotFoundByIdException(dto.getOrderId()));

    if (!(order.getAuthId().equals(reviewerId))) {
      throw new NotYourOrderException(reviewerId);
    }

    if (reviewRepository.findByCustomProduct_Orders(order).isPresent()) {
      throw new ReviewAlreadyExistByOrderException();
    }

    Review review = Review.create(dto.getRating(), dto.getContent());
    review.associate(product);

    if (images != null){
      addImage(images, review);
    }
    reviewRepository.save(review);
  }
  private void addImage(List<MultipartFile> imageFiles, Review review) {
    if (imageFiles.size() > Review.IMAGES_MAX_COUNT)
      throw new BadRequestException(1 + " <= Size of images <= " + Review.IMAGES_MAX_COUNT);
    for (MultipartFile imageFile : imageFiles) {
      String reviewImageUrl = s3Uploader.upload(imageFile);
      review.addImage(ReviewImage.create(review, reviewImageUrl));
    }
  }
}