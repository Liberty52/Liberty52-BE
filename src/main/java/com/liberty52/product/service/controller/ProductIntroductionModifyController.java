package com.liberty52.product.service.controller;


import com.liberty52.product.service.applicationservice.ProductIntroductionModifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ProductIntroductionModifyController {
    private final ProductIntroductionModifyService productIntroductionModifyService;

    @PatchMapping("/admin/product/{productId}/introduction")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyProductIntroductionByAdmin(@RequestHeader("LB-Role") String role, @PathVariable String productId,
                                                 @RequestPart(value = "images",required = false) MultipartFile productIntroductionImageFile){
            productIntroductionModifyService.modifyProductIntroduction(role, productId, productIntroductionImageFile);
    }

}
