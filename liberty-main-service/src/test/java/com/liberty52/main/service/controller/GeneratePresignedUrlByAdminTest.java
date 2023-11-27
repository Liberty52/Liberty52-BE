package com.liberty52.main.service.controller;

import com.liberty52.main.global.adapter.StoragePresigner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GeneratePresignedUrlByAdminController.class)
class GeneratePresignedUrlByAdminTest {
    @MockBean
    private StoragePresigner storagePresigner;

    @Autowired
    private MockMvc mvc;

    @Test
    public void success() throws Exception {
        String url = "https://liberty52.s3.ap-northeast-2.amazonaws.com/product/temp/ed82386d-3d42-4bef-a599-7ad4c807c3de.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20231127T055636Z&X-Amz-SignedHeaders=content-type%3Bhost&X-Amz-Expires=119&X-Amz-Credential=AKIAYRFV77DWKFY5M56R%2F20231127%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Signature=b986ad1aa40e46f8ad8b0e4845cae193b383d6dffe44bfae72d4f2567622d32c";
        given(storagePresigner.generatePresignedUrl(any(), any()))
            .willReturn(new URL(url));

        mvc.perform(
            get("/admin/presigned-url")
                .contentType(MediaType.APPLICATION_JSON)
                .header("LB-Role", "ADMIN")
                .param("contentType", StoragePresigner.BlobContentType.IMAGE_JPEG.name())
                .param("blobKey", "some/blob/key")
        ).andExpectAll(
            status().isOk(),
            content().string(url)
        );
    }
}
