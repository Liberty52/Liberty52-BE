package com.liberty52.main.service.controller.dto;

import com.liberty52.main.service.entity.ProductState;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @Min(1)
    private Integer productOrder;
}
