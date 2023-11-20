package com.liberty52.main.service.applicationservice.mock;

import com.liberty52.main.service.applicationservice.impl.LicenseImageRetrieveServiceImpl;
import com.liberty52.main.service.controller.dto.LicenseImageRetrieveByAdminDto;
import com.liberty52.main.service.controller.dto.LicenseImageRetrieveDto;
import com.liberty52.main.service.entity.LicenseImage;
import com.liberty52.main.service.repository.LicenseImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.liberty52.main.global.constants.RoleConstants.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class LicenseImageRetrieveMockTest {
	@InjectMocks
	LicenseImageRetrieveServiceImpl licenseImageRetrieveService;

	@Mock
	LicenseImageRepository licenseImageRepository;

	@Test
	void retrieveLicenseImagesTest() {
		// Given
		LocalDate startDate = LocalDate.of(2023, 1, 1);
		LocalDate endDate = startDate.plusDays(10);
		LicenseImage mockLicense = LicenseImage.builder()
				.artistName("testArtistName")
				.artName("testArtName")
				.startDate(startDate)
				.endDate(endDate)
				.licenseImageUrl("mockImageUrl")
				.stock(10)
				.build();
		given(licenseImageRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate.now(),
				LocalDate.now())).willReturn(Collections.singletonList(mockLicense));
		// When
		List<LicenseImageRetrieveDto> result = licenseImageRetrieveService.retrieveLicenseImages();

		verify(licenseImageRepository, times(1)).findByStartDateLessThanEqualAndEndDateGreaterThanEqual(any(), any());
		assertEquals(1, result.size());
		assertEquals("testArtistName", result.get(0).getArtistName());
		assertEquals("testArtName", result.get(0).getArtName());
		assertEquals(startDate, result.get(0).getStartDate());
		assertEquals(endDate, result.get(0).getEndDate());
		assertEquals("mockImageUrl", result.get(0).getImageUrl());
		assertEquals(10, result.get(0).getStock());
	}

	@Test
	void retrieveLicenseImagesByAdminTest() {
		// Given
		LocalDate startDate = LocalDate.of(2023, 1, 1);
		LocalDate endDate = startDate.plusDays(10);
		LicenseImage mockLicense = LicenseImage.builder()
				.artistName("testArtistName")
				.artName("testArtName")
				.startDate(startDate)
				.endDate(endDate)
				.licenseImageUrl("mockImageUrl")
				.stock(10)
				.build();
		given(licenseImageRepository.findAll()).willReturn(Collections.singletonList(mockLicense));

		// When
		List<LicenseImageRetrieveByAdminDto> result = licenseImageRetrieveService.retrieveLicenseImagesByAdmin(ADMIN);

		// Then: 검증 로직 추가 (예: findAll 메소드 호출 확인 및 반환 값 확인)
		verify(licenseImageRepository, times(1)).findAll();
		assertEquals(1, result.size());
		assertEquals("testArtistName", result.get(0).getArtistName());
	}

	@Test
	void retrieveLicenseImageDetailsByAdminTest() {
		// Given
		LocalDate startDate = LocalDate.of(2023, 1, 1);
		LocalDate endDate = startDate.plusDays(10);
		LicenseImage mockLicense = LicenseImage.builder()
				.artistName("testArtistName")
				.artName("testArtName")
				.startDate(startDate)
				.endDate(endDate)
				.licenseImageUrl("mockImageUrl")
				.stock(10)
				.build();
		given(licenseImageRepository.findById(any())).willReturn(java.util.Optional.of(mockLicense));

		// When
		LicenseImageRetrieveByAdminDto result = licenseImageRetrieveService.retrieveLicenseImageDetailsByAdmin(ADMIN,
				"testId");

		// Then: 검증 로직 추가 (예: findAll 메소드 호출 확인 및 반환 값 확인)
		verify(licenseImageRepository, times(1)).findById(any());
		assertEquals("testArtistName", result.getArtistName());
		assertEquals("testArtName", result.getArtName());
		assertEquals(startDate, result.getStartDate());
		assertEquals(endDate, result.getEndDate());
		assertEquals("mockImageUrl", result.getLicenseImageUrl());
		assertEquals(10, result.getStock());
	}
}
