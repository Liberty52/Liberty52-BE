package com.liberty52.product.service.controller;


import com.liberty52.product.service.applicationservice.ProductIntroductionModifyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "상품", description = "상품 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class ProductIntroductionModifyController {
    private final ProductIntroductionModifyService productIntroductionModifyService;

    @Operation(summary = "상품 소개 수정", description = "관리자가 특정 상품의 소개 이미지를 수정합니다.")
    @PatchMapping("/admin/product/{productId}/introduction")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyProductIntroductionByAdmin(@RequestHeader("LB-Role") String role, @PathVariable String productId,
                                                 @RequestPart(value = "images",required = false) MultipartFile productIntroductionImageFile){
            productIntroductionModifyService.modifyProductIntroduction(role, productId, productIntroductionImageFile);
    }

}
