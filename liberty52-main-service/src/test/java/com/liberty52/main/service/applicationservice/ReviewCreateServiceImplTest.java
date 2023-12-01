package com.liberty52.main.service.applicationservice;

import com.liberty52.main.MockS3Test;
import com.liberty52.main.global.exception.external.badrequest.ReviewAlreadyExistByCustomProductException;
import com.liberty52.main.global.exception.external.badrequest.ReviewCannotWriteByOrderStatusIsNotCompleteException;
import com.liberty52.main.global.exception.external.badrequest.ReviewCannotWriteInCartException;
import com.liberty52.main.service.controller.dto.ReviewCreateRequestDto;
import com.liberty52.main.service.entity.*;
import com.liberty52.main.service.repository.CustomProductRepository;
import com.liberty52.main.service.repository.OrdersRepository;
import com.liberty52.main.service.repository.ProductRepository;
import com.liberty52.main.service.repository.ReviewRepository;
import com.liberty52.main.service.utils.MockFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.liberty52.main.service.utils.MockConstants.MOCK_AUTH_ID;
import static com.liberty52.main.service.utils.MockFactory.createCustomProduct;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ReviewCreateServiceImplTest extends MockS3Test {

    private final List<MultipartFile> testImageList = new ArrayList<>();
    private final String mockImageUrl = "src/test/resources/static/test.jpg";
    private final Integer rating = 3;
    private final String content = "is very nice review";
    @Autowired
    EntityManager em;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomProductRepository customProductRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewCreateService reviewCreateService;
    private MockMultipartFile imageFile;
    private String mockOrderCustomProductId;
    private Orders order;
    private CustomProduct customProduct;

    @BeforeEach
    void setMockCustomProductData() throws IOException, NoSuchFieldException, IllegalAccessException {
        imageFile = new MockMultipartFile("image", "test.png", "image/jpeg",
                new FileInputStream(mockImageUrl));

        Product product = MockFactory.createProduct("Liberty 52_Frame", ProductState.ON_SALE,
                10_000_000L, true,1);
        productRepository.save(product);

        order = MockFactory.createOrder(MOCK_AUTH_ID);
        Field orderStatus = order.getClass().getDeclaredField("orderStatus");
        orderStatus.setAccessible(true);
        orderStatus.set(order, OrderStatus.COMPLETE);
        ordersRepository.save(order);

        customProduct = MockFactory.createCustomProduct(mockImageUrl, 1, MOCK_AUTH_ID);
        customProduct.associateWithOrder(order);
        customProduct.associateWithProduct(product);
        customProductRepository.save(customProduct);
        mockOrderCustomProductId = customProduct.getId();
//    em.flush();
//    em.clear();
//    System.out.println("BeforeEach!");
    }

    @Test
    void createReview() {
        //given

        testImageList.add(imageFile);
        ReviewCreateRequestDto dto = ReviewCreateRequestDto.createForTest(rating, content, mockOrderCustomProductId);
        //when
        reviewCreateService.createReview(MOCK_AUTH_ID, dto, testImageList);


        Review review = reviewRepository.findByCustomProduct(customProduct).get();
        //then
        Assertions.assertEquals(rating, review.getRating());
        Assertions.assertEquals(content, review.getContent());

    }

    @Test
    void validateReviewInCart() {
        //given
        Cart cart = MockFactory.createCart(MOCK_AUTH_ID);
        CustomProduct cartCustomProduct = createCustomProduct(mockImageUrl, 1, MOCK_AUTH_ID);
        cartCustomProduct.associateWithCart(cart);
        String mockCartCustomProductId = cartCustomProduct.getId();
        customProductRepository.save(cartCustomProduct);
        testImageList.add(imageFile);
        ReviewCreateRequestDto dto = ReviewCreateRequestDto.createForTest(rating, content,
                mockCartCustomProductId);
        //when
        //then
        Assertions.assertThrows(ReviewCannotWriteInCartException.class,
                () -> reviewCreateService.createReview(MOCK_AUTH_ID, dto, testImageList));
    }

    @Test
    void validateOrderStatusIsComplete() {
        //given
        order.changeOrderStatusToCancelRequest();
        ordersRepository.save(order);
        customProduct.associateWithOrder(order);
        customProductRepository.save(customProduct);
        mockOrderCustomProductId = customProduct.getId();

        testImageList.add(imageFile);
        ReviewCreateRequestDto dto = ReviewCreateRequestDto.createForTest(rating, content,
                mockOrderCustomProductId);
        //when
        //then
        Assertions.assertThrows(ReviewCannotWriteByOrderStatusIsNotCompleteException.class,
                () -> reviewCreateService.createReview(MOCK_AUTH_ID, dto, testImageList));
    }

    @Test
    void validateReviewAlreadyExist() {
        //given
        createReview();
        ReviewCreateRequestDto dto = ReviewCreateRequestDto.createForTest(rating, content,
                mockOrderCustomProductId);
        //when
        //then
        Assertions.assertThrows(ReviewAlreadyExistByCustomProductException.class,
                () -> reviewCreateService.createReview(MOCK_AUTH_ID, dto, testImageList));
    }

}
