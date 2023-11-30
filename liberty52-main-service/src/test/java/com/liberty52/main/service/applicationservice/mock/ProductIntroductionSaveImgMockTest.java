package com.liberty52.main.service.applicationservice.mock;

import com.liberty52.main.global.adapter.s3.S3UploaderApi;
import com.liberty52.main.service.applicationservice.impl.ProductIntroductionImgSaveServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import static com.liberty52.main.global.constants.RoleConstants.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductIntroductionSaveImgMockTest {
    @InjectMocks
    ProductIntroductionImgSaveServiceImpl productIntroductionImgSaveService;
    @Mock
    S3UploaderApi s3Uploader;

    @Test
    void saveProductIntroductionImageTest() {
        MultipartFile productIntroductionTestImage = mock(MultipartFile.class);
        // Given
        when(s3Uploader.upload(productIntroductionTestImage)).thenReturn("MockImageUrl");

        // When
        String url = productIntroductionImgSaveService.saveProductIntroductionImageByAdmin(ADMIN,
                productIntroductionTestImage);

        // Then: 검증 로직 추가 (예: createContent 메소드 호출 확인)
        verify(s3Uploader).upload(productIntroductionTestImage);
        assertEquals("MockImageUrl", url);
    }

}
