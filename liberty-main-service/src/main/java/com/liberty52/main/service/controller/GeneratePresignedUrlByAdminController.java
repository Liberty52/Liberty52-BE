package com.liberty52.main.service.controller;

import com.liberty52.main.global.adapter.StoragePresigner;
import com.liberty52.main.global.annotation.NoServiceUse;
import com.liberty52.main.global.util.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@NoServiceUse
public class GeneratePresignedUrlByAdminController {
    private final StoragePresigner storagePresigner;

    @GetMapping("/admin/presigned-url")
    public String generatePresignedUrlByAdmin(
        @RequestHeader("LB-Role") String role,
        @RequestParam String blobKey,
        @RequestParam StoragePresigner.BlobContentType contentType
    ) {
        Validator.isAdmin(role);
        return storagePresigner.generatePresignedUrl(blobKey, contentType).toString();
    }
}
