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

    @Test
    @DisplayName("web.rest 패키지 내의 클래스는 Controller로 끝나고, RestController 어노테이션을 가지고 있어야 합니다")
    void controllerClassTest() {
        ArchRule rule = classes()
                .that().resideInAnyPackage("..web.rest")
                .should().haveSimpleNameEndingWith("Controller");

        ArchRule annotationRule = classes()
                .that().resideInAnyPackage("..web.rest")
                .should().beAnnotatedWith(RestController.class);

        rule.check(javaClasses);
        annotationRule.check(javaClasses);
    }


    /********************************  service*/

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


}
