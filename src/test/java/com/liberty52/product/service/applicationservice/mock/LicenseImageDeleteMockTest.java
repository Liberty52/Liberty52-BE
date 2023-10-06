package com.liberty52.product.service.applicationservice.mock;

import static com.liberty52.product.global.constants.RoleConstants.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.liberty52.product.service.applicationservice.impl.LicenseImageDeleteServiceImpl;
import com.liberty52.product.service.controller.dto.LicenseImageModifyDto;
import com.liberty52.product.service.entity.LicenseImage;
import com.liberty52.product.service.repository.LicenseImageRepository;

@ExtendWith(MockitoExtension.class)
class LicenseImageDeleteMockTest {
	@InjectMocks
	private LicenseImageDeleteServiceImpl licenseImageDeleteService;

	@Mock
	private LicenseImageRepository licenseImageRepository;

	@Test
	void deleteLicenseImageTest() {
		// Given
		LocalDate startDate = LocalDate.of(2023, 1, 1);
		LocalDate endDate = startDate.plusDays(10);
		LicenseImageModifyDto dto = LicenseImageModifyDto.createForTest("testArtistName", "testArtName",
			startDate, endDate, 10);

		LicenseImage licenseImage = LicenseImage.builder()
			.artistName(dto.getArtistName())
			.artName(dto.getArtName())
			.startDate(dto.getStartDate())
			.endDate(dto.getEndDate())
			.stock(dto.getStock())
			.licenseImageUrl("mockImageUrl")
			.build();
		String licenseImageId = "testId";
		when(licenseImageRepository.findById(licenseImageId)).thenReturn(Optional.of(licenseImage));

		// When
		licenseImageDeleteService.deleteLicenseImage(ADMIN, licenseImageId);

		// Then
		verify(licenseImageRepository).findById(anyString());
		verify(licenseImageRepository).delete(any(LicenseImage.class));
	}

}