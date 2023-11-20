package com.liberty52.main.service.applicationservice.mock;

import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.service.applicationservice.impl.ProductIntroductionUpsertServiceImpl;
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
class ProductIntroductionUpsertMockTest {
	@InjectMocks
	ProductIntroductionUpsertServiceImpl productIntroductionUpsertService;

	@Mock
	ProductRepository productRepository;

	@Test
	void upsertProductIntroductionTest() {
		// Given
		String productId = "testProductId";
		String content = "testContent";
		Product mockProduct = mock(Product.class);
		given(productRepository.findById(anyString())).willReturn(Optional.of(mockProduct));

		// When
		productIntroductionUpsertService.upsertProductIntroductionByAdmin(ADMIN, productId, content);

		// Then: 검증 로직 추가 (예: createContent 메소드 호출 확인)
		verify(mockProduct).createContent(content);
	}

	@Test
	void upsertProductIntroductionTestWhenProductIsNull() {
		// Given
		String productId = "testProductId";
		String content = "testContent";
		// When
		given(productRepository.findById(anyString())).willReturn(Optional.empty());
		// Then: 검증 로직 추가 (예: 이미지가 등록되어 있을 때 예외가 발생하는지 확인)
		assertThrows(ResourceNotFoundException.class,
				() -> productIntroductionUpsertService.upsertProductIntroductionByAdmin(ADMIN, productId, content));
	}

}
