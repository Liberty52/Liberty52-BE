package com.liberty.authentication.test.context.support;

import com.liberty.authentication.core.context.AuthenticationContext;
import com.liberty.authentication.core.context.AuthenticationContextHolderStrategy;
import com.liberty.authentication.test.context.TestAuthenticationContextHolderStrategyAdapter;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestContextAnnotationUtils;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.function.Supplier;

public class WithAuthenticationContextTestExecutionListener extends AbstractTestExecutionListener {

    static final AuthenticationContextHolderStrategy DEFAULT_CONTEXT_HOLDER_STRATEGY = new TestAuthenticationContextHolderStrategyAdapter();

    Converter<TestContext, AuthenticationContextHolderStrategy> authenticationContextHolderStrategyConverter = (testContext) -> {
        if (!testContext.hasApplicationContext()) {
            return DEFAULT_CONTEXT_HOLDER_STRATEGY;
        }
        ApplicationContext context = testContext.getApplicationContext();
        if (context.getBeanNamesForType(AuthenticationContextHolderStrategy.class).length == 0) {
            return DEFAULT_CONTEXT_HOLDER_STRATEGY;
        }
        return context.getBean(AuthenticationContextHolderStrategy.class);
    };

    @Override
    public void beforeTestMethod(@NonNull TestContext testContext) {
        TestAuthenticationContext testAuthContext = createTestAuthContext(testContext.getTestMethod(), testContext);
        if (testAuthContext == null) {
            testAuthContext = createTestAuthContext(testContext.getTestClass(), testContext);
        }
        if (testAuthContext == null) {
            return;
        }
        Supplier<AuthenticationContext> supplier = testAuthContext.getAuthenticationContextSupplier();
        if (testAuthContext.getTestExecutionEvent() == LBTestExecutionEvent.TEST_METHOD) {
            this.authenticationContextHolderStrategyConverter.convert(testContext).setContext(supplier.get());
//            AuthenticationContextHolder.setContext(supplier.get());
        }
    }

    private TestAuthenticationContext createTestAuthContext(AnnotatedElement annotated, TestContext context) {
        WithAuthenticationContext withContext = AnnotatedElementUtils.findMergedAnnotation(annotated,
                WithAuthenticationContext.class);
        return createTestAuthContext(annotated, withContext, context);
    }

    private TestAuthenticationContext createTestAuthContext(Class<?> annotated, TestContext context) {
        TestContextAnnotationUtils.AnnotationDescriptor<WithAuthenticationContext> withAuthenticationContextAnnotationDescriptor = TestContextAnnotationUtils
                .findAnnotationDescriptor(annotated, WithAuthenticationContext.class);
        if (withAuthenticationContextAnnotationDescriptor == null) {
            return null;
        }
        WithAuthenticationContext withAuthenticationContext = withAuthenticationContextAnnotationDescriptor.getAnnotation();
        Class<?> rootDeclaringClass = withAuthenticationContextAnnotationDescriptor.getRootDeclaringClass();
        return createTestAuthContext(rootDeclaringClass, withAuthenticationContext, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private TestAuthenticationContext createTestAuthContext(
            AnnotatedElement element,
            WithAuthenticationContext withContext,
            TestContext testContext
    ) {
        if (withContext == null) return null;

        withContext = AnnotationUtils.synthesizeAnnotation(withContext, element);
        WithAuthenticationContextFactory factory = createFactory(withContext, testContext);
        Class<? extends Annotation> type = (Class<? extends Annotation>) GenericTypeResolver
                .resolveTypeArgument(factory.getClass(), WithAuthenticationContextFactory.class);
        Annotation annotation = findAnnotation(element, type);
        Supplier<AuthenticationContext> supplier = () -> {
            try {
                return factory.createAuthenticationContext(annotation);
            }
            catch (RuntimeException ex) {
                throw new IllegalStateException("Unable to create Authentication using " + annotation, ex);
            }
        };
        LBTestExecutionEvent initialize = withContext.setupBefore();
        return new TestAuthenticationContext(supplier, initialize);
    }

    @SuppressWarnings({"deprecation", "DataFlowIssue"})
    private Annotation findAnnotation(AnnotatedElement annotated, Class<? extends Annotation> type) {
        Annotation findAnnotation = AnnotatedElementUtils.findMergedAnnotation(annotated, type);
        if (findAnnotation != null) {
            return findAnnotation;
        }
        Annotation[] allAnnotations = AnnotationUtils.getAnnotations(annotated);
        for (Annotation annotationToTest : allAnnotations) {
            WithAuthenticationContext withAuthenticationContext = AnnotationUtils.findAnnotation(
                    annotationToTest.annotationType(), WithAuthenticationContext.class);
            if (withAuthenticationContext != null) {
                return annotationToTest;
            }
        }
        return null;
    }

    private WithAuthenticationContextFactory<? extends Annotation> createFactory(
            WithAuthenticationContext withSecurityContext,
            TestContext testContext
    ) {
        Class<? extends WithAuthenticationContextFactory<? extends Annotation>> clazz = withSecurityContext.factory();
        try {
            return testContext.getApplicationContext().getAutowireCapableBeanFactory().createBean(clazz);
        }
        catch (IllegalStateException ex) {
            return BeanUtils.instantiateClass(clazz);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    static class TestAuthenticationContext {

        private final Supplier<AuthenticationContext> authenticationContextSupplier;

        private final LBTestExecutionEvent testExecutionEvent;

        TestAuthenticationContext(Supplier<AuthenticationContext> authenticationContextSupplier, LBTestExecutionEvent testExecutionEvent) {
            this.authenticationContextSupplier = authenticationContextSupplier;
            this.testExecutionEvent = testExecutionEvent;
        }

        Supplier<AuthenticationContext> getAuthenticationContextSupplier() {
            return this.authenticationContextSupplier;
        }

        LBTestExecutionEvent getTestExecutionEvent() {
            return this.testExecutionEvent;
        }

    }
}
