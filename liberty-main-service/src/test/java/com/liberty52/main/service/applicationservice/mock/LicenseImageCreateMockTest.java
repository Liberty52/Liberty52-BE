package com.liberty52.main.service.applicationservice.mock;

import com.liberty52.main.global.adapter.s3.S3UploaderApi;
import com.liberty52.main.service.applicationservice.impl.LicenseImageCreateServiceImpl;
import com.liberty52.main.service.controller.dto.LicenseImageCreateDto;
import com.liberty52.main.service.entity.LicenseImage;
import com.liberty52.main.service.repository.LicenseImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static com.liberty52.main.global.constants.RoleConstants.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

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
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = startDate.plusDays(10);
        given(s3Uploader.upload(multipartFile)).willReturn("mockImageUrl");
        LicenseImageCreateDto dto = LicenseImageCreateDto.createForTest("testArtistName", "testArtName",
                startDate, endDate, 10);

        // When
        licenseImageCreateService.createLicenseImage(ADMIN, dto, multipartFile);

        // Then: 검증 로직 추가 (예: createProductIntroduction 메소드 호출 확인)
        ArgumentCaptor<LicenseImage> captor = ArgumentCaptor.forClass(LicenseImage.class);
        verify(licenseImageRepository, times(1)).save(captor.capture());
        assertEquals("testArtistName", captor.getValue().getArtistName());
        assertEquals("testArtName", captor.getValue().getArtName());
        assertEquals(startDate, captor.getValue().getStartDate());
        assertEquals(endDate, captor.getValue().getEndDate());
        assertEquals("mockImageUrl", captor.getValue().getLicenseImageUrl());
        assertEquals(10, captor.getValue().getStock().intValue());
    }
}