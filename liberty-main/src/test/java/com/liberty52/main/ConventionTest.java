package com.liberty52.main;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ConventionTest {

    JavaClasses javaClasses;

    @BeforeEach
    public void beforeEach() {
        javaClasses = new ClassFileImporter()
                .withImportOption(new ImportOption.DoNotIncludeTests())
                .importPackages("com.liberty52.main");
    }

    @Test
    @DisplayName("controller 패키지 내의 클래스는 Controller로 끝나고, Controller 또는 RestController 어노테이션을 가지고 있어야 합니다")
    public void controllerClassTest() {
        ArchRule rule = classes()
                .that().resideInAnyPackage("..controller")
                .should().haveSimpleNameEndingWith("Controller");

        ArchRule annotationRule = classes()
                .that().resideInAnyPackage("..controller")
                .should().beAnnotatedWith(RestController.class)
                .orShould().beAnnotatedWith(Controller.class);

        rule.check(javaClasses);
        annotationRule.check(javaClasses);
    }

    @Test
    @DisplayName("applicationservice 패키지 내의 클래스는 Service로 끝나야 하고, 인터페이스여야 합니다.")
    public void applicationServicTest() {
        ArchRule rule = classes()
                .that().resideInAnyPackage("..applicationservice")
                .should().haveSimpleNameEndingWith("Service")
                .andShould().beInterfaces();

        rule.check(javaClasses);
    }


    @Test
    @DisplayName("applicationservice.impl 패키지 내의 클래스는 ServiceImpl로 끝나고 Service 어노테이션을 가지고 있어야 합니다.")
    public void ApplicationServiceImplTest() {
        ArchRule rule = classes()
                .that().resideInAnyPackage("..applicationservice.impl")
                .and().areTopLevelClasses()
                .should().haveSimpleNameEndingWith("ServiceImpl")
                .andShould().beAnnotatedWith(Service.class);

        rule.check(javaClasses);
    }

    @Test
    @DisplayName("Entity 패키지 내의 클래스는 @Setter, @Data 어노테이션을 가지고 있어선 안됩니다.")
    public void entityTest() {
        ArchRule rule = classes()
                .that().resideInAnyPackage("..entity")
                .should().notBeAnnotatedWith("lombok.Setter")
                .andShould().notBeAnnotatedWith("lombok.Data");

        rule.check(javaClasses);
    }

    @Test
    @DisplayName("global.config 패키지 내의 클래스는 Config로 끝나야하고 @Configuration 어노테이션을 가지고 있어야 합니다.")
    public void configTest() {
        ArchRule rule = classes()
                .that().resideInAnyPackage("..global.config")
                .should().haveSimpleNameEndingWith("Config")
                .andShould().beAnnotatedWith(Configuration.class);

        rule.check(javaClasses);
    }

    @Test
    @DisplayName("global.intercepter 패키지 내의 클래스는 Interceptor로 끝나야 합니다.")
    public void intercepterTest() {
        ArchRule rule = classes()
                .that().resideInAnyPackage("..global.intercepter")
                .should().haveSimpleNameEndingWith("Interceptor");

        rule.check(javaClasses);
    }

}
