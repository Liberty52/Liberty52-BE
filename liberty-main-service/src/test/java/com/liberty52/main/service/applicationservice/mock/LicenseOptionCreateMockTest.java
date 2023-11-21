package com.liberty52.main.service.applicationservice.mock;

import com.liberty52.main.global.exception.external.badrequest.BadRequestException;
import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.service.applicationservice.impl.LicenseOptionCreateServiceImpl;
import com.liberty52.main.service.controller.dto.LicenseOptionCreateRequestDto;
import com.liberty52.main.service.entity.Product;
import com.liberty52.main.service.entity.license.LicenseOption;
import com.liberty52.main.service.repository.LicenseOptionRepository;
import com.liberty52.main.service.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.liberty52.main.global.constants.RoleConstants.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

		assertEquals("testName", captor.getValue().getName());
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

	@Test
	void createLicenseOptionByAdminTest_ProductIsCustom() {
		// Given
		LicenseOptionCreateRequestDto dto = new LicenseOptionCreateRequestDto("testName");
		String productId = "testProductId";
		Product mockProduct = mock(Product.class);
		when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
		when(mockProduct.isCustom()).thenReturn(true);
		// When
		// Then
		assertThrows(BadRequestException.class,
				() -> licenseOptionCreateService.createLicenseOptionByAdmin(ADMIN, dto, productId));
	}

	@Test
	void createLicenseOptionByAdminTest_Already_License_Option_is_Existed() {
		// Given
		LicenseOptionCreateRequestDto dto = new LicenseOptionCreateRequestDto("testName");
		String productId = "testProductId";
		Product mockProduct = mock(Product.class);
		when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
		when(licenseOptionRepository.findByProductId(productId)).thenReturn(Optional.of(mock(LicenseOption.class)));
		// When
		// Then
		assertThrows(BadRequestException.class,
				() -> licenseOptionCreateService.createLicenseOptionByAdmin(ADMIN, dto, productId));
	}
}
