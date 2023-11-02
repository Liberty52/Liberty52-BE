package com.liberty52.authentication.test.configurer.web;

import com.liberty52.authentication.configurer.AuthenticationAspectConfiguration;
import com.liberty52.authentication.test.context.support.AuthenticationTestExecutionListeners;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@WebMvcTest
@AutoConfigureMockMvc
@Import(AuthenticationAspectConfiguration.class)
@AuthenticationTestExecutionListeners
public @interface LBWebMvcTest {

    @AliasFor(annotation = WebMvcTest.class, attribute = "properties")
    String[] properties() default {};

    @AliasFor(annotation = WebMvcTest.class, attribute = "value")
    Class<?>[] value() default {};

    @AliasFor(annotation = WebMvcTest.class, attribute = "controllers")
    Class<?>[] controllers() default {};

    @AliasFor(annotation = WebMvcTest.class, attribute = "useDefaultFilters")
    boolean useDefaultFilters() default true;

    @AliasFor(annotation = WebMvcTest.class, attribute = "includeFilters")
    ComponentScan.Filter[] includeFilters() default {};

    @AliasFor(annotation = WebMvcTest.class, attribute = "excludeFilters")
    ComponentScan.Filter[] excludeFilters() default {};

    @AliasFor(annotation = WebMvcTest.class, attribute = "excludeAutoConfiguration")
    Class<?>[] excludeAutoConfiguration() default {};

    @AliasFor(annotation = AutoConfigureMockMvc.class, attribute = "addFilters")
    boolean addFilters() default false;

}
