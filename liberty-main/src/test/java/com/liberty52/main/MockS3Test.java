package com.liberty52.main;

import com.liberty52.main.global.adapter.s3.S3Uploader;
import com.liberty52.main.global.adapter.s3.S3UploaderApi;
import com.liberty52.main.global.exception.internal.S3UploaderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MockS3Test {
    @Mock //2023.09.18 nullPointError로 인해 MockBean --> Mock으로 변경
    protected S3UploaderApi s3UploaderApi;

    @Mock //2023.09.18 nullPointError로 인해 MockBean --> Mock으로 변경
    protected S3Uploader s3Uploader;

    @BeforeEach
    public void initMockBeans() {
        Mockito.lenient().when(s3UploaderApi.upload(any())).thenReturn("mock s3 return");
        Mockito.lenient().doNothing().when(s3UploaderApi).delete(any());
        try {
            Mockito.lenient().when(s3Uploader.upload(any())).thenReturn("mock s3 return");
            Mockito.lenient().doNothing().when(s3Uploader).delete(any());
        } catch (S3UploaderException e) {
            throw new RuntimeException(e);
        }
    }
}
