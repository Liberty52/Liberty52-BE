package com.liberty52.main.global.adapter.stablediffusion.dto;

import lombok.Getter;

@Getter
public abstract class StableDiffusionResponse<O> {
    private String status;
    private Double generateTime;
    private String id;
    private O output;
}
