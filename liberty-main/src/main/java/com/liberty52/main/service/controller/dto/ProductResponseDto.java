package com.liberty52.main.service.controller.dto;

import com.liberty52.main.service.entity.ProductState;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    private String id;
    private String name;
    private ProductState productState;
    private Long price;
    private boolean isCustom;
    private String pictureUrl;

}
