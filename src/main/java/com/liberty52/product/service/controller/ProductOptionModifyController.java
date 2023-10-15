package com.liberty52.product.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.liberty52.product.service.applicationservice.ProductOptionModifyService;
import com.liberty52.product.service.controller.dto.ProductOptionModifyRequestDto;

@Tag(name = "상품", description = "상품 관련 API를 제공합니다")
@RequiredArgsConstructor
@RestController
public class ProductOptionModifyController {



}
