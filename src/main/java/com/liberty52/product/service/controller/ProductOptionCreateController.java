package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.ProductOptionCreateService;
import com.liberty52.product.service.controller.dto.CreateProductOptionRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "상품", description = "상품 관련 API를 제공합니다")
@RestController
@RequiredArgsConstructor
public class ProductOptionCreateController {



}
