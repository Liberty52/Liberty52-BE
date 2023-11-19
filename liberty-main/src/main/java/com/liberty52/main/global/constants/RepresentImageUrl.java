package com.liberty52.main.global.constants;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class RepresentImageUrl {

    public static String LIBERTY52_FRAME_REPRESENTATIVE_URL;
    private final Environment env;

    public RepresentImageUrl(Environment env) {
        this.env = env;
        LIBERTY52_FRAME_REPRESENTATIVE_URL = env.getProperty("product.representative-url.liberty52-frame");
    }
}
