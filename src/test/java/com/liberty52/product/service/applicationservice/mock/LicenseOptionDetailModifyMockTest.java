package com.liberty52.product.service.applicationservice.mock;

import static com.liberty52.product.global.constants.RoleConstants.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.liberty52.product.global.adapter.s3.S3UploaderApi;
import com.liberty52.product.global.exception.external.badrequest.BadRequestException;
import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.product.service.applicationservice.impl.LicenseOptionDetailModifyServiceImpl;
import com.liberty52.product.service.controller.dto.LicenseOptionDetailModifyDto;
import com.liberty52.product.service.entity.license.LicenseOptionDetail;
import com.liberty52.product.service.repository.LicenseOptionDetailRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LicenseOptionDetailModifyMockTest {

	@InjectMocks
	LicenseOptionDetailModifyServiceImpl licenseOptionDetailModifyService;

	@Mock
	LicenseOptionDetailRepository licenseOptionDetailRepository;

	@Mock
	S3UploaderApi s3Uploader;

	@Test
	void modifyLicenseOptionDetailByAdminTest() {
		// Given
		String licenseOptionDetailId = "testLicenseOptionDetailId";
		LocalDate startDate = LocalDate.of(2023, 1, 1);
		LocalDate endDate = startDate.plusDays(10);
		LicenseOptionDetailModifyDto dto = new LicenseOptionDetailModifyDto("ArtName", "ArtistName", 10, 1000, true,
			startDate, endDate);
		MultipartFile artImageFile = mock(MultipartFile.class);

		LicenseOptionDetail mockLicenseOptionDetail = mock(LicenseOptionDetail.class);
		when(licenseOptionDetailRepository.findById(licenseOptionDetailId)).thenReturn(
			Optional.of(mockLicenseOptionDetail));
		when(s3Uploader.upload(artImageFile)).thenReturn("modifiedArtUrl");

		// When
		licenseOptionDetailModifyService.modifyLicenseOptionDetailByAdmin(ADMIN, licenseOptionDetailId, dto,
			artImageFile);

		// Then
		verify(mockLicenseOptionDetail, times(1)).modifyLicenseOptionDetail(dto, "modifiedArtUrl");
	}

	@Test
	void modifyLicenseOptionDetailByAdmin_When_LicenseOptionDetailNotFoundTest() {
		// Given
		String licenseOptionDetailId = "testLicenseOptionDetailId";
		LocalDate startDate = LocalDate.of(2023, 1, 1);
		LocalDate endDate = startDate.plusDays(10);
		LicenseOptionDetailModifyDto dto = new LicenseOptionDetailModifyDto("ArtName", "ArtistName", 10, 1000, true,
			startDate, endDate);
		MultipartFile artImageFile = mock(MultipartFile.class);

		when(licenseOptionDetailRepository.findById(licenseOptionDetailId)).thenReturn(Optional.empty());

		assertThrows(
			ResourceNotFoundException.class,
			() -> licenseOptionDetailModifyService.modifyLicenseOptionDetailByAdmin(ADMIN, licenseOptionDetailId, dto,
				artImageFile));
	}

	@Test
	void modifyLicenseOptionDetailByAdmin_When_ArtImageFileIsNullTest() {
		// Given
		String licenseOptionDetailId = "testLicenseOptionDetailId";
		LocalDate startDate = LocalDate.of(2023, 1, 1);
		LocalDate endDate = startDate.plusDays(10);
		LicenseOptionDetailModifyDto dto = new LicenseOptionDetailModifyDto("ArtName", "ArtistName", 10, 1000, true,
			startDate, endDate);
		MultipartFile artImageFile = null;

		LicenseOptionDetail mockLicenseOptionDetail = mock(LicenseOptionDetail.class);
		when(licenseOptionDetailRepository.findById(licenseOptionDetailId)).thenReturn(
			Optional.of(mockLicenseOptionDetail));

		// When
		// Then
		assertThrows(BadRequestException.class,
			() -> licenseOptionDetailModifyService.modifyLicenseOptionDetailByAdmin(ADMIN, licenseOptionDetailId, dto,
				artImageFile));
	}

	@Test
	void modifyLicenseOptionDetailOnSailStateByAdminTest() {
		// Given
		String licenseOptionDetailId = "testLicenseOptionDetailId";

		LicenseOptionDetail mockLicenseOptionDetail = mock(LicenseOptionDetail.class);
		when(licenseOptionDetailRepository.findById(licenseOptionDetailId)).thenReturn(
			Optional.of(mockLicenseOptionDetail));

		// When
		licenseOptionDetailModifyService.modifyLicenseOptionDetailOnSailStateByAdmin(ADMIN, licenseOptionDetailId);

		// Then
		verify(mockLicenseOptionDetail, times(1)).updateOnSale();
	}

	@Test
	void modifyLicenseOptionDetailOnSailStateByAdmin_When_LicenseOptionDetailNotFoundTest() {
		// Given
		String licenseOptionDetailId = "testLicenseOptionDetailId";

		when(licenseOptionDetailRepository.findById(licenseOptionDetailId)).thenReturn(Optional.empty());

		// When
		// Then
		assertThrows(
			ResourceNotFoundException.class,
			() -> licenseOptionDetailModifyService.modifyLicenseOptionDetailOnSailStateByAdmin(ADMIN,
				licenseOptionDetailId));
	}
}
