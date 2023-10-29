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
import com.liberty52.product.service.applicationservice.impl.LicenseOptionModifyServiceImpl;
import com.liberty52.product.service.controller.dto.LicenseOptionModifyRequestDto;
import com.liberty52.product.service.entity.license.LicenseOption;
import com.liberty52.product.service.repository.LicenseOptionRepository;

@ExtendWith(MockitoExtension.class)
class LicenseOptionModifyMockTest {

	@InjectMocks
	LicenseOptionModifyServiceImpl licenseOptionModifyService;

	@Mock
	LicenseOptionRepository licenseOptionRepository;

	@Test
	void modifyLicenseOptionByAdminTest() {
		// Given
		String licenseOptionId = "testLicenseOptionId";
		LicenseOptionModifyRequestDto dto = new LicenseOptionModifyRequestDto("modifiedName", false, true);

		LicenseOption mockLicenseOption = mock(LicenseOption.class);
		when(licenseOptionRepository.findById(licenseOptionId)).thenReturn(Optional.of(mockLicenseOption));

		// When
		licenseOptionModifyService.modifyLicenseOptionByAdmin(ADMIN, licenseOptionId, dto);

		// Then
		verify(mockLicenseOption, times(1)).modifyLicenseOption(dto.getName(), dto.getRequire(), dto.getOnSale());
	}

	@Test
	void modifyLicenseOptionByAdminTest_LicenseOptionNotFound() {
		// Given
		String licenseOptionId = "testLicenseOptionId";
		LicenseOptionModifyRequestDto dto = new LicenseOptionModifyRequestDto("modifiedName", false, true);

		when(licenseOptionRepository.findById(licenseOptionId)).thenReturn(Optional.empty());

		// Then
		assertThrows(ResourceNotFoundException.class,
			() -> licenseOptionModifyService.modifyLicenseOptionByAdmin(ADMIN, licenseOptionId, dto));
	}
}
