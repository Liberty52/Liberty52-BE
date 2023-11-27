package com.liberty52.main;

import com.liberty52.main.global.annotation.NoServiceUse;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class DependencyTest {
    JavaClasses javaClasses;

    @BeforeEach
    public void beforeEach() {
        javaClasses = new ClassFileImporter()
                .withImportOption(new ImportOption.DoNotIncludeTests())
                .importPackages("com.liberty52.main");
    }


    @Test
    @DisplayName("Controller는 Service와 controller.dto를 사용할 수 있음.")
    public void controllerDependencyTest() {
        ArchRule rule = classes()
                .that().resideInAnyPackage("..controller").and().areNotAnnotatedWith(NoServiceUse.class)
                .should().dependOnClassesThat()
                .resideInAnyPackage("..controller.dto", "..applicationservice");

        rule.check(javaClasses);
    }

    @Test
    @DisplayName("Controller는 의존되지 않음.")
    public void controllerDependencyTest2() {
        ArchRule rule = classes()
                .that().resideInAnyPackage("..controller")
                .should().onlyHaveDependentClassesThat()
                .resideInAnyPackage("..controller");

        rule.check(javaClasses);
    }

    @Test
    @DisplayName("Controller는 Entity와 applicationservice.impl을 사용할 수 없음.")
    public void controllerDependencyTest3() {
        ArchRule rule = noClasses()
                .that().resideInAnyPackage("..controller")
                .should().dependOnClassesThat()
                .areNotEnums()
                .andShould().resideInAnyPackage("..entity", "..applicationservice.impl");

        rule.check(javaClasses);
    }

    @Test
    @DisplayName("Service는 Controller를 의존하면 안됨")
    public void serviceDependencyTest() {
        ArchRule rule = noClasses()
                .that().resideInAnyPackage("..applicationservice")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..controller");

        rule.check(javaClasses);
    }

}
