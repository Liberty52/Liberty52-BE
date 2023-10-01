package com.liberty52.product.service.applicationservice.mock;

import static com.liberty52.product.global.constants.RoleConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.liberty52.product.global.adapter.s3.S3UploaderApi;
import com.liberty52.product.service.applicationservice.impl.LicenseImageCreateServiceImpl;
import com.liberty52.product.service.controller.dto.LicenseImageCreateDto;
import com.liberty52.product.service.entity.LicenseImage;
import com.liberty52.product.service.repository.LicenseImageRepository;

@ExtendWith(MockitoExtension.class)
class LicenseImageCreateMockTest {
	@InjectMocks
	LicenseImageCreateServiceImpl licenseImageCreateService;

	@Mock
	LicenseImageRepository licenseImageRepository;

	@Mock
	S3UploaderApi s3Uploader;

	@Test
	void createLicenseImageTest() {
		// Given
		MultipartFile multipartFile = mock(MultipartFile.class);
		LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime endDate = startDate.plusDays(10);
		given(s3Uploader.upload(multipartFile)).willReturn("mockImageUrl");
		LicenseImageCreateDto dto = LicenseImageCreateDto.createForTest("testArtistName", "testWorkName",
			startDate, endDate, 10);

		// When
		licenseImageCreateService.createLicenseImage(ADMIN, dto, multipartFile);

		// Then: 검증 로직 추가 (예: createProductIntroduction 메소드 호출 확인)
		ArgumentCaptor<LicenseImage> captor = ArgumentCaptor.forClass(LicenseImage.class);
		verify(licenseImageRepository, times(1)).save(captor.capture());
		assertEquals("testArtistName", captor.getValue().getArtistName());
		assertEquals("testWorkName", captor.getValue().getWorkName());
		assertEquals(startDate, captor.getValue().getStartDate());
		assertEquals(endDate, captor.getValue().getEndDate());
		assertEquals("mockImageUrl", captor.getValue().getLicenseImageUrl());
		assertEquals(10, captor.getValue().getStock().intValue());
	}
}