package com.liberty52.main.service.applicationservice.mock;

import com.liberty52.main.global.adapter.s3.S3UploaderApi;
import com.liberty52.main.global.exception.external.forbidden.InvalidRoleException;
import com.liberty52.main.service.applicationservice.impl.ProductCreateServiceImpl;
import com.liberty52.main.service.controller.dto.ProductCreateRequestDto;
import com.liberty52.main.service.entity.Product;
import com.liberty52.main.service.entity.ProductState;
import com.liberty52.main.service.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductCreateServiceMockTest {
    private final String Role = "ADMIN";
    @InjectMocks
    private ProductCreateServiceImpl productCreateService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private S3UploaderApi s3Uploader;

    @Test
    void 제품추가_성공() {
        //given
        MultipartFile productTestImage = mock(MultipartFile.class);
        ProductCreateRequestDto testDto = ProductCreateRequestDto.builder()
                .name("testName")
                .price(100L)
                .productState(ProductState.ON_SALE)
                .isCustom(true)
                .build();

        when(s3Uploader.upload(productTestImage)).thenReturn("MockImageUrl");

        //when
        productCreateService.createProductByAdmin(Role, testDto, productTestImage);

        //then
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository, times(1)).save(captor.capture());
        assertEquals("testName", captor.getValue().getName());
        assertEquals(100L, captor.getValue().getPrice());
        assertEquals(ProductState.ON_SALE, captor.getValue().getProductState());
        assertTrue(captor.getValue().isCustom());
        assertEquals("MockImageUrl", captor.getValue().getPictureUrl());
    }

    @Test
    void 제품추가_잘못된권한() {
        //given
        MultipartFile productTestImage = mock(MultipartFile.class);
        ProductCreateRequestDto testDto = ProductCreateRequestDto.builder()
                .name("testName")
                .price(100L)
                .productState(ProductState.ON_SALE)
                .isCustom(true)
                .build();

        //when & then
        Assertions.assertThrows(InvalidRoleException.class, () -> productCreateService.createProductByAdmin("Normal", testDto, productTestImage));
    }
}
