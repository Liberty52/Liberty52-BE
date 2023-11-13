package com.liberty52.product.service.controller;


import com.liberty52.product.service.applicationservice.ProductCreateService;
import com.liberty52.product.service.applicationservice.ProductRemoveService;
import com.liberty52.product.service.applicationservice.ProductModifyService;
import com.liberty52.product.service.controller.dto.ProductCreateRequestDto;
import com.liberty52.product.service.controller.dto.ProductModifyRequestDto;
import com.liberty52.product.service.controller.dto.ProductResponseDto;
import com.liberty52.product.service.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "상품", description = "상품 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
/*ProductController 는 상품의 옵션, 소개 등 세부 정보를 제외한 상품의 가장 기본적인 데이터를 활용하는 엔드포인트들을 다루는 용도입니다.
상품의 세부 정보와 관련된 엔드포인트들은 ProductInfoController 에서 다룹니다.*/
public class ProductAdminController {

    private final ModelMapper mapper;
    private final ProductCreateService productCreateService;
    private final ProductModifyService productModifyService;
    private final ProductRemoveService productRemoveService;

    /**CREATE**/
    @Operation(summary = "상품 생성", description = "관리자가 상품을 생성합니다.")
    @PostMapping("/admin/product")
    public ResponseEntity<ProductResponseDto> createProductByAdmin(@RequestHeader("LB-Role") String role,
                                                                               @Valid @RequestPart(value = "data") ProductCreateRequestDto productRequestDto,
                                                                               @RequestPart(value = "image", required = false) MultipartFile productImage) {
        Product savedProduct = productCreateService.createProductByAdmin(role, productRequestDto, productImage);
        ProductResponseDto result = mapper.map(savedProduct, ProductResponseDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**READ**/

    /**UPDATE**/
    @Operation(summary = "상품 수정", description = "관리자가 상품을 수정합니다.")
    @PatchMapping("/admin/product/{productId}")
    public ResponseEntity<ProductResponseDto> modifyProductByAdmin(@RequestHeader("LB-Role") String role,
                                                                               @PathVariable String productId,
                                                                               @Valid @RequestPart(value = "data", required = false) ProductModifyRequestDto productRequestDto,
                                                                               @RequestPart(value = "image", required = false) MultipartFile productImage) {
        Product modifiedProduct = productModifyService.modifyProductByAdmin(role, productId, productRequestDto, productImage);
        ProductResponseDto result = mapper.map(modifiedProduct, ProductResponseDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**DELETE**/
    @Operation(summary = "상품 삭제", description = "관리자가 상품을 삭제합니다.")
    @DeleteMapping("/admin/product/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductByAdmin(@RequestHeader("LB-Role") String role, @PathVariable String productId) {
        productRemoveService.removeProductByAdmin(role, productId);
    }

}
