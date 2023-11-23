package com.liberty52.main.service.applicationservice.mock;

import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.service.applicationservice.impl.ProductIntroductionDeleteServiceImpl;
import com.liberty52.main.service.entity.Product;
import com.liberty52.main.service.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.liberty52.main.global.constants.RoleConstants.ADMIN;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductIntroductionDeleteMockTest {
    @InjectMocks
    ProductIntroductionDeleteServiceImpl productIntroductionDeleteService;

    @Mock
    ProductRepository productRepository;

    @Test
    void deleteProductIntroductionTest() {
        // Given
        String productId = "testProductId";
        Product mockProduct = mock(Product.class);
        given(productRepository.findById(anyString())).willReturn(Optional.of(mockProduct));

        // When
        productIntroductionDeleteService.deleteProductIntroduction(ADMIN, productId);

        // Then: 검증 로직 추가 (예: createContent 메소드 호출 확인)
        verify(mockProduct).deleteContent();
    }

    @Test
    void deleteProductIntroductionTestWhenProductIsNull() {
        // Given
        String productId = "testProductId";
        // When
        given(productRepository.findById(anyString())).willReturn(Optional.empty());
        // Then: 검증 로직 추가 (예: 이미지가 등록되어 있을 때 예외가 발생하는지 확인)
        assertThrows(ResourceNotFoundException.class,
                () -> productIntroductionDeleteService.deleteProductIntroduction(ADMIN, productId));
    }

}
