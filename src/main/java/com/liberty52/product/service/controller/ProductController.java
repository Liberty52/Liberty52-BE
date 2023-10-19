package com.liberty52.product.service.controller;


import com.liberty52.product.service.applicationservice.ProductCreateService;
import com.liberty52.product.service.controller.dto.ProductRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "상품", description = "상품 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
/*ProductController 는 상품의 옵션, 소개 등 세부 정보를 제외한 상품의 가장 기본적인 데이터를 활용하는 엔드포인트들을 다루는 용도입니다.
상품의 세부 정보와 관련된 엔드포인트들은 ProductInfoController 에서 다룹니다.*/
public class ProductController {

    private final ProductCreateService productCreateService;

    /**CREATE**/
    @Operation(summary = "상품 생성", description = "관리자가 상품을 생성합니다.")
    @PostMapping("/admin/product")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProductIntroductionByAdmin(@RequestHeader("LB-Role") String role,
                                                 @Valid @RequestPart(value = "data") ProductRequestDto productRequestDto,
                                                 @RequestPart(value = "image", required = false) MultipartFile productImage) {
        productCreateService.createProductByAdmin(role, productRequestDto, productImage);
    }
    /**READ**/
    /**UPDATE**/
    /**DELETE**/

}
