package com.liberty52.product.global.adapter;

import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.List;

public interface TextToImageGenerator {
    Mono<List<String>> generate(String prompt, Dimension size, Integer n);
}
