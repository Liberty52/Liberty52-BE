package com.liberty52.product;


import com.liberty.authentication.test.context.support.AuthenticationTestExecutionListeners;
import com.liberty52.product.global.config.AspectConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({AspectConfig.class})
@AuthenticationTestExecutionListeners
public @interface ApplyLBMockUser {
}
