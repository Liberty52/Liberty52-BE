package com.liberty.authentication.test.context.support;


import org.springframework.test.context.TestExecutionListeners;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@TestExecutionListeners(
        inheritListeners = false,
        listeners = { WithAuthenticationContextTestExecutionListener.class },
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
public @interface AuthenticationTestExecutionListeners {
}
