package com.liberty52.product.global.adapter.stablediffusion.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
public abstract class StableDiffusionResponse<O> {
    private String status;
    private Double generateTime;
    private String id;
    private O output;
}
