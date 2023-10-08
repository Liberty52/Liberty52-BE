package com.liberty52.product.global.adapter;

import com.liberty52.product.global.util.Result;
import lombok.Getter;

public interface ImageUpscaler {
    Result<String> upscale(String srcUri, Scale scale);

    sealed class Scale {
        @Getter
        public static final class Mul extends Scale {
            private final Double width;
            private final Double height;

            public Mul(Double width) {
                this.width = this.height = width;
            }

            public Mul(Double width, Double height) {
                this.width = width;
                this.height = height;
            }
        }

        @Getter
        public static final class Fix extends Scale {
            private final Long width;
            private final Long height;

            public Fix(Long length) {
                this.width = this.height = length;
            }

            public Fix(Long width, Long height) {
                this.width = width;
                this.height = height;
            }
        }
    }
}
