package com.liberty52.product.service.applicationservice.mock;

import static com.liberty52.product.global.constants.RoleConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.product.service.applicationservice.impl.LicenseOptionCreateServiceImpl;
import com.liberty52.product.service.controller.dto.LicenseOptionCreateRequestDto;
import com.liberty52.product.service.entity.Product;
import com.liberty52.product.service.entity.license.LicenseOption;
import com.liberty52.product.service.repository.LicenseOptionRepository;
import com.liberty52.product.service.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class LicenseOptionCreateMockTest {

	@InjectMocks
	LicenseOptionCreateServiceImpl licenseOptionCreateService;

	@Mock
	ProductRepository productRepository;

	@Mock
	LicenseOptionRepository licenseOptionRepository;

	@Test
	void createLicenseOptionByAdminTest() {
		// Given
		LicenseOptionCreateRequestDto dto = new LicenseOptionCreateRequestDto("testName");
		String productId = "testProductId";

		Product mockProduct = mock(Product.class);
		when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

		// When
		licenseOptionCreateService.createLicenseOptionByAdmin(ADMIN, dto, productId);
		licenseOptionRepository.save(LicenseOption.create(dto.getName()));

		// Then
		ArgumentCaptor<LicenseOption> captor = ArgumentCaptor.forClass(LicenseOption.class);
		verify(licenseOptionRepository, times(1)).save(captor.capture());

		assertEquals("testName", captor.getValue().getName ());
	}

	@Test
	void createLicenseOptionByAdminTest_ProductNotFound() {
		// Given
		LicenseOptionCreateRequestDto dto = new LicenseOptionCreateRequestDto("testName");
		String productId = "testProductId";
		// When
		when(productRepository.findById(productId)).thenReturn(Optional.empty());
		// Then
		assertThrows(ResourceNotFoundException.class,
			() -> licenseOptionCreateService.createLicenseOptionByAdmin(ADMIN, dto, productId));
	}
}
