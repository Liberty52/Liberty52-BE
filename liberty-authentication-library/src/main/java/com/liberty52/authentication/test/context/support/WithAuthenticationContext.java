package com.liberty52.authentication.test.context.support;

import java.lang.annotation.*;

@Target({ ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface WithAuthenticationContext {
    Class<? extends WithAuthenticationContextFactory<? extends Annotation>> factory();

    LBTestExecutionEvent setupBefore() default LBTestExecutionEvent.TEST_METHOD;
}
