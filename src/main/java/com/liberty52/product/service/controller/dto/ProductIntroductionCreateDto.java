package com.liberty52.product.service.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductIntroductionCreateDto {

	@NotEmpty
	@Size(max = 10000)
	String content;

}
