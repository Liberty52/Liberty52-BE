package com.liberty52.product.service.controller.dto;

import com.liberty52.product.service.entity.ProductState;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequestDto {
    @NotBlank
    private String name;

    @NotNull
    private ProductState productState;

    @Min(0)
    private Long price;

    @NotNull
    private Boolean isCustom;
}
