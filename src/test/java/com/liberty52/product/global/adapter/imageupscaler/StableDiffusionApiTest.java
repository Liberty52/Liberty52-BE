package com.liberty52.product.global.adapter.imageupscaler;

import com.liberty52.product.global.adapter.ImageUpscaler;
import com.liberty52.product.global.util.Result;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Disabled // 비용이 발생하는 테스트입니다. 이 어노테이션을 지우지 말아주세요.
class StableDiffusionApiTest {
    @Autowired
    private ImageUpscaler imageUpscaler;

    @Test
    void success() {
        Result<String> result = imageUpscaler.upscale(
            "https://liberty52.s3.ap-northeast-2.amazonaws.com/product/custom/0009388f-09b4-459b-82b1-49bd5ad6de64.jpg",
            new ImageUpscaler.Scale.Mul(2.0)
        );
        assertTrue(result.isSuccess());
        assertNotNull(result.getOrNull());
    }
}
