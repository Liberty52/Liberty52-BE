package com.liberty52.auth;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class ConventionTest {

    JavaClasses javaClasses;

    @BeforeEach
    public void beforeEach() {
        javaClasses = new ClassFileImporter()
                .withImportOption(new ImportOption.DoNotIncludeTests())
                .importPackages("com.liberty52.auth");
    }


    /* *******************************  web rest*/

    @Test
    @DisplayName("web.rest 패키지 내의 클래스는 Controller로 끝나야 합니다")
    void controllerTest() {
        ArchRule rule = classes()
                .that().resideInAnyPackage("..web.rest")
                .should().haveSimpleNameEndingWith("Controller");
        rule.check(javaClasses);
    }

    @Test
    @DisplayName("web.rest 패키지 내의 클래스는 RestController 어노테이션을 가지고 있어야 합니다")
    void controllerTest2() {
        ArchRule annotationRule = classes()
                .that().resideInAnyPackage("..web.rest")
                .should().beAnnotatedWith(RestController.class);
        annotationRule.check(javaClasses);
    }


    /* *******************************  service*/

    @Test
    @DisplayName("Service 패키지 안의 클래스는 Impl 또는 Service로 끝나야 합니다.")
    void ServiceTest() {
        ArchRule rule = classes()
                .that()
                .resideInAnyPackage("..service..")
                .and().areTopLevelClasses()
                .should().haveSimpleNameEndingWith("Service")
                .orShould().haveSimpleNameEndingWith("Impl");
        rule.check(javaClasses);
    }

    @Test
    @DisplayName("Service 패키지 안의 Service로 끝나는 클래스는 인터페이스여야 합니다.")
    void ServiceTest2() {
        ArchRule rule = classes()
                .that()
                .resideInAnyPackage("..service..")
                .and().haveSimpleNameEndingWith("Service")
                .should().beInterfaces();
        rule.check(javaClasses);
    }

    @Test
    @DisplayName("Service 패키지 안의 Impl로 끝나는 클래스는 Impl로 끝나는 클래스는 인터페이스이고 service 어노테이션을 가져야 합니다.")
    void ServiceTest3() {
        ArchRule rule = classes()
                .that()
                .resideInAnyPackage("..service..")
                .and().haveSimpleNameEndingWith("Impl")
                .should().beAnnotatedWith(org.springframework.stereotype.Service.class);
        rule.check(javaClasses);
    }


    /* *******************************  repository*/

    @Test
    @DisplayName("Repository 패키지 안의 클래스는 Repository로 끝나야 합니다.")
    void RepositoryTest() {
        ArchRule rule = classes()
                .that()
                .resideInAnyPackage("..repository..")
                .should().haveSimpleNameEndingWith("Repository");
        rule.check(javaClasses);
    }

    @Test
    @DisplayName("Repository 패키지 안의 Repository로 끝나는 클래스는 인터페이스여야 합니다.")
    void RepositoryTest2() {
        ArchRule rule = classes()
                .that()
                .resideInAnyPackage("..repository..")
                .should().beInterfaces();
        rule.check(javaClasses);
    }


    /* *******************************  global*/

    @Test
    @DisplayName("global.config 패키지 안의 클래스는 Config로 끝나야 합니다.")
    void ConfigTest() {
        ArchRule rule = classes()
                .that()
                .areTopLevelClasses()
                .and()
                .resideInAnyPackage("..global.config..")
                .should().haveSimpleNameEndingWith("Config");
        rule.check(javaClasses);
    }

}
