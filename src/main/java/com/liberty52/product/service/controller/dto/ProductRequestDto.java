package com.liberty52.product.service.controller.dto;

import com.liberty52.product.service.entity.ProductState;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Getter;


@Getter
public class ProductRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private ProductState productState;

    @Min(0)
    private Long price;

    @NotNull
    private Boolean isCustom;


}
