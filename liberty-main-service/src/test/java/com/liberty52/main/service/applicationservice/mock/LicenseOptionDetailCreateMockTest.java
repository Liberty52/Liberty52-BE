package com.liberty52.main.service.applicationservice.mock;

import com.liberty52.main.global.adapter.s3.S3UploaderApi;
import com.liberty52.main.global.exception.external.badrequest.BadRequestException;
import com.liberty52.main.service.applicationservice.impl.LicenseOptionDetailCreateServiceImpl;
import com.liberty52.main.service.controller.dto.LicenseOptionDetailCreateDto;
import com.liberty52.main.service.entity.license.LicenseOption;
import com.liberty52.main.service.entity.license.LicenseOptionDetail;
import com.liberty52.main.service.repository.LicenseOptionDetailRepository;
import com.liberty52.main.service.repository.LicenseOptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;

import static com.liberty52.main.global.constants.RoleConstants.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LicenseOptionDetailCreateMockTest {

    @InjectMocks
    LicenseOptionDetailCreateServiceImpl licenseOptionDetailCreateService;

    @Mock
    LicenseOptionRepository licenseOptionRepository;

    @Mock
    LicenseOptionDetailRepository licenseOptionDetailRepository;

    @Mock
    S3UploaderApi s3Uploader;

    @Test
    void createLicenseOptionDetailByAdminTest() {
        // Given
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = startDate.plusDays(10);
        String licenseOptionId = "testLicenseOptionId";
        LicenseOptionDetailCreateDto dto = LicenseOptionDetailCreateDto.create
                ("testArtName", "testArtistName", 100, true, 1000, startDate, endDate);
        MultipartFile artImageFile = mock(MultipartFile.class);
        LicenseOption mockLicenseOption = mock(LicenseOption.class);

        when(licenseOptionRepository.findById(licenseOptionId)).thenReturn(Optional.of(mockLicenseOption));
        when(s3Uploader.upload(artImageFile)).thenReturn("testArtUrl");

        // When
        licenseOptionDetailCreateService.createLicenseOptionDetailByAdmin(ADMIN, dto, licenseOptionId, artImageFile);

        // Then
        ArgumentCaptor<LicenseOptionDetail> captor = ArgumentCaptor.forClass(LicenseOptionDetail.class);
        verify(licenseOptionDetailRepository, times(1)).save(captor.capture());
        LicenseOptionDetail savedLicenseOptionDetail = captor.getValue();
        assertEquals(dto.getArtName(), savedLicenseOptionDetail.getArtName());
        assertEquals(dto.getArtistName(), savedLicenseOptionDetail.getArtistName());
        assertEquals(dto.getStock(), savedLicenseOptionDetail.getStock());
        assertEquals(dto.getOnSale(), savedLicenseOptionDetail.getOnSale());
        assertEquals(dto.getPrice(), savedLicenseOptionDetail.getPrice());
        assertEquals("testArtUrl", savedLicenseOptionDetail.getArtUrl());
    }

    @Test
    void createLicenseOptionDetailByAdmin_WhenLicenseOptionNotFound_ThrowException() {
        // Given
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = startDate.plusDays(10);
        String licenseOptionId = "testLicenseOptionId";
        LicenseOptionDetailCreateDto dto = LicenseOptionDetailCreateDto.create
                ("testArtName", "testArtistName", 100, true, 1000, startDate, endDate);
        MultipartFile artImageFile = mock(MultipartFile.class);

        when(licenseOptionRepository.findById(licenseOptionId)).thenReturn(Optional.empty());

        // When
        // Then
        assertThrows(RuntimeException.class, () -> {
            licenseOptionDetailCreateService.createLicenseOptionDetailByAdmin(ADMIN, dto, licenseOptionId,
                    artImageFile);
        });
    }

    @Test
    void createLicenseOptionDetailByAdmin_WhenRoleIsNotAdmin() {
        // Given
        String licenseOptionId = "testLicenseOptionId";
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = startDate.plusDays(10);
        LicenseOptionDetailCreateDto dto = LicenseOptionDetailCreateDto.create
                ("testArtName", "testArtistName", 100, true, 1000, startDate, endDate);
        MultipartFile artImageFile = mock(MultipartFile.class);

        // When
        // Then
        assertThrows(RuntimeException.class, () -> {
            licenseOptionDetailCreateService.createLicenseOptionDetailByAdmin("USER", dto, licenseOptionId,
                    artImageFile);
        });
    }

    @Test
    void createLicenseOptionDetailByAdmin_WhenArtImageFileIsNull() {
        // Given
        String licenseOptionId = "testLicenseOptionId";
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = startDate.plusDays(10);
        LicenseOptionDetailCreateDto dto = LicenseOptionDetailCreateDto.create
                ("testArtName", "testArtistName", 100, true, 1000, startDate, endDate);
        MultipartFile artImageFile = null;
        LicenseOption mockLicenseOption = mock(LicenseOption.class);

        when(licenseOptionRepository.findById(licenseOptionId)).thenReturn(Optional.of(mockLicenseOption));
        // When
        // Then
        assertThrows(BadRequestException.class,
                () -> licenseOptionDetailCreateService.createLicenseOptionDetailByAdmin(ADMIN, dto, licenseOptionId, artImageFile));

    }
}
