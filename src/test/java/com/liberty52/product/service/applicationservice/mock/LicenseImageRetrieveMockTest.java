package com.liberty52.product.service.applicationservice.mock;

import static com.liberty52.product.global.constants.RoleConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.liberty52.product.service.applicationservice.impl.LicenseImageRetrieveServiceImpl;
import com.liberty52.product.service.controller.dto.LicenseImageRetrieveDto;
import com.liberty52.product.service.entity.LicenseImage;
import com.liberty52.product.service.repository.LicenseImageRepository;

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
			.workName("testWorkName")
			.startDate(startDate)
			.endDate(endDate)
			.licenseImageUrl("mockImageUrl")
			.stock(10)
			.build();
		given(licenseImageRepository.findAll()).willReturn(Collections.singletonList(mockLicense));

		// When
		List<LicenseImageRetrieveDto> result = licenseImageRetrieveService.retrieveLicenseImages(ADMIN);

		// Then: 검증 로직 추가 (예: findAll 메소드 호출 확인 및 반환 값 확인)
		verify(licenseImageRepository, times(1)).findAll();
		assertEquals(1, result.size());
		assertEquals("testArtistName", result.get(0).getArtistName());
	}
}
