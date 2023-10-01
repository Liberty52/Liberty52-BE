package com.liberty52.product.service.applicationservice.mock;


import com.liberty52.product.global.adapter.s3.S3UploaderApi;
import com.liberty52.product.global.exception.internal.S3UploaderException;
import com.liberty52.product.service.applicationservice.impl.ProductIntroductionModifyServiceImpl;
import com.liberty52.product.service.entity.Product;
import com.liberty52.product.service.entity.ProductState;
import com.liberty52.product.service.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.liberty52.product.global.constants.RoleConstants.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductIntroductionModifyMockTest {
    @InjectMocks
    ProductIntroductionModifyServiceImpl productIntroductionModifyService;

    @Mock
    ProductRepository productRepository;

    @Mock
    S3UploaderApi s3Uploader;

    @Test
    void 제품소개수정() throws S3UploaderException {
        /*-----------<Test Case 1(prev image->new image)>-----------*/
        /**Given**/
        String productId = "testProductId";
        MultipartFile multipartFile = mock(MultipartFile.class);
        //generate testProduct
        Product testProduct = Product.builder().name("test").productState(ProductState.ON_SALE).price(0L).build();
        //assume that testProduct has previous image
        testProduct.createProductIntroduction("previousMockImageUrl");

        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));
        when(s3Uploader.upload(multipartFile)).thenReturn("newMockImageUrl");

        /**When**/
        productIntroductionModifyService.modifyProductIntroduction(ADMIN, productId, multipartFile);

        /**Then**/
        assertEquals("newMockImageUrl", testProduct.getProductIntroductionImageUrl());

        /*-----------<Test Case 2(image -> no image)>-----------*/
        /**Given**/
        String product2Id = "testProductId2";
        Product testProduct2 = Product.builder().name("test2").productState(ProductState.ON_SALE).price(0L).build();
        //assume that testProduct has previous image
        testProduct2.createProductIntroduction("previousMockImageUrl");

        when(productRepository.findById(product2Id)).thenReturn(Optional.of(testProduct2));

        /**When**/
        productIntroductionModifyService.modifyProductIntroduction(ADMIN, product2Id, null);

        /**Then**/
        assertEquals(null, testProduct2.getProductIntroductionImageUrl());

        /*-----------<Test Case 3(no image -> new image)>-----------*/
        /**Given**/
        String product3Id = "testProductId2";
        MultipartFile multipartFile3 = mock(MultipartFile.class);
        Product testProduct3 = Product.builder().name("test3").productState(ProductState.ON_SALE).price(0L).build();
        //assume that testProduct has no previous image
        testProduct2.createProductIntroduction("null");

        when(productRepository.findById(product3Id)).thenReturn(Optional.of(testProduct3));
        when(s3Uploader.upload(multipartFile3)).thenReturn("newMockImageUrl");

        /**When**/
        productIntroductionModifyService.modifyProductIntroduction(ADMIN, product3Id, multipartFile3);

        /**Then**/
        assertEquals("newMockImageUrl", testProduct3.getProductIntroductionImageUrl());

    }

}
