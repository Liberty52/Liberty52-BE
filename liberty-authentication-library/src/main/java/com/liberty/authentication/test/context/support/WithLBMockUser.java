package com.liberty.authentication.test.context.support;

import com.liberty.authentication.core.UserRole;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@WithAuthenticationContext(factory = WithLBMockUserAuthenticationFactory.class)
public @interface WithLBMockUser {

    String id() default "1";

    UserRole role() default UserRole.USER;

    @AliasFor(annotation = WithAuthenticationContext.class)
    LBTestExecutionEvent setupBefore() default LBTestExecutionEvent.TEST_METHOD;
}
