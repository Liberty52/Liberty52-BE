package com.liberty52.main.global.adapter;

import lombok.Getter;
import reactor.core.publisher.Mono;

public interface ImageUpscaler {
    Mono<String> upscale(String srcUri, Scale scale);

    sealed interface Scale {
        @Getter
        final class Mul implements Scale {
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
        final class Fix implements Scale {
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
