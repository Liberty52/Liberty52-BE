package com.liberty52.main.service.applicationservice.mock;

import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.service.applicationservice.impl.LicenseOptionModifyServiceImpl;
import com.liberty52.main.service.controller.dto.LicenseOptionModifyRequestDto;
import com.liberty52.main.service.entity.license.LicenseOption;
import com.liberty52.main.service.repository.LicenseOptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.liberty52.main.global.constants.RoleConstants.ADMIN;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
		LicenseOptionModifyRequestDto dto = new LicenseOptionModifyRequestDto("modifiedName");

		LicenseOption mockLicenseOption = mock(LicenseOption.class);
		when(licenseOptionRepository.findById(licenseOptionId)).thenReturn(Optional.of(mockLicenseOption));

		// When
		licenseOptionModifyService.modifyLicenseOptionByAdmin(ADMIN, licenseOptionId, dto);

		// Then
		verify(mockLicenseOption, times(1)).modifyLicenseOption(dto.getName());
	}

	@Test
	void modifyLicenseOptionByAdminTest_LicenseOptionNotFound() {
		// Given
		String licenseOptionId = "testLicenseOptionId";
		LicenseOptionModifyRequestDto dto = new LicenseOptionModifyRequestDto("modifiedName");

		when(licenseOptionRepository.findById(licenseOptionId)).thenReturn(Optional.empty());

		// Then
		assertThrows(ResourceNotFoundException.class,
				() -> licenseOptionModifyService.modifyLicenseOptionByAdmin(ADMIN, licenseOptionId, dto));
	}
}
