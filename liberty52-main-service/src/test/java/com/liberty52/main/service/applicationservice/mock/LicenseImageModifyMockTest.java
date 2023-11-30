package com.liberty52.main.service.applicationservice.mock;

import com.liberty52.main.global.adapter.s3.S3UploaderApi;
import com.liberty52.main.service.applicationservice.impl.LicenseImageModifyServiceImpl;
import com.liberty52.main.service.controller.dto.LicenseImageModifyDto;
import com.liberty52.main.service.entity.LicenseImage;
import com.liberty52.main.service.repository.LicenseImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;

import static com.liberty52.main.global.constants.RoleConstants.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class LicenseImageModifyMockTest {
    @InjectMocks
    private LicenseImageModifyServiceImpl licenseImageModifyService;

    @Mock
    private LicenseImageRepository licenseImageRepository;

    @Mock
    private S3UploaderApi s3Uploader;

    @Test
    void modifyLicenseImageTestWhenImageIsNotNull() {
        // Given
        MultipartFile multipartFile = mock(MultipartFile.class);
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
                .licenseImageUrl("previousImageUrl")
                .build();

        when(licenseImageRepository.findById(licenseImage.getId())).thenReturn(Optional.of(licenseImage));
        given(s3Uploader.upload(multipartFile)).willReturn("modifiedImageUrl");
        // When
        licenseImageModifyService.modifyLicenseImage(ADMIN, dto, multipartFile, licenseImage.getId());
        // Then
        verify(licenseImageRepository).findById(licenseImage.getId());
        verify(s3Uploader).upload(multipartFile);

        assertEquals("modifiedImageUrl", licenseImage.getLicenseImageUrl());
    }

    @Test
    void modifyLicenseImageTestWhenImageIsNull() {
        // Given
        MultipartFile multipartFile = null;
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = startDate.plusDays(10);

        LicenseImageModifyDto dto = LicenseImageModifyDto.createForTest("modifiedArtistName", "modifiedArtName",
                startDate, endDate, 20);

        LicenseImage licenseImage = LicenseImage.builder()
                .artistName("originalArtistName")
                .artName("originalArtName")
                .startDate(LocalDate.of(2022, 1, 1))
                .endDate(LocalDate.of(2022, 12, 31))
                .stock(10)
                .licenseImageUrl("previousImageUrl")
                .build();

        when(licenseImageRepository.findById(licenseImage.getId())).thenReturn(Optional.of(licenseImage));

        // When
        licenseImageModifyService.modifyLicenseImage(ADMIN, dto, multipartFile, licenseImage.getId());

        // Then
        verify(licenseImageRepository).findById(anyString());

        // s3 가 불릴일이 없으니 never()
        verify(s3Uploader, never()).upload(any(MultipartFile.class));

        assertEquals(dto.getArtistName(), licenseImage.getArtistName());
        assertEquals(dto.getArtName(), licenseImage.getArtName());
        assertEquals(dto.getStartDate(), licenseImage.getStartDate());
        assertEquals(dto.getEndDate(), licenseImage.getEndDate());
        assertEquals(dto.getStock(), licenseImage.getStock());
        assertEquals("previousImageUrl", licenseImage.getLicenseImageUrl());
    }
}
