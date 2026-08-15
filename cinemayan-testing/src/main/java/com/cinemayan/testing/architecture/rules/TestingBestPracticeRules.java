package com.cinemayan.testing.architecture.rules;

import com.cinemayan.testing.architecture.bases.BaseIntegrationTest;
import com.cinemayan.testing.architecture.bases.BaseUnitTest;
import com.cinemayan.testing.architecture.rules.predicates.TestMethodNamingArchCondition;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.cinemayan.testing.architecture.rules.CleanArchitectureRules.DOMAIN_PACKAGE;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.GeneralCodingRules.testClassesShouldResideInTheSamePackageAsImplementation;

public interface TestingBestPracticeRules extends BaseUnitTest {

    String TEST_SUFFIX = "Test";

    String INTEGRATION_TEST_SUFFIX = "IT";

    @ArchTest
    ArchRule TEST_CLASSES_PLACEMENT = testClassesShouldResideInTheSamePackageAsImplementation();

    @ArchTest
    ArchRule TEST_CLASSES_SHOULD_BE_PACKAGE_PRIVATE = classes().that()
        .haveSimpleNameEndingWith(TEST_SUFFIX)
        .should()
        .notHaveModifier(JavaModifier.ABSTRACT)
        .andShould()
        .bePackagePrivate();

    @ArchTest
    ArchRule TEST_CLASSES_SHOULD_EXTEND_BASE_UNIT_TEST = classes().that()
        .haveSimpleNameEndingWith(TEST_SUFFIX)
        .should()
        .beAssignableTo(BaseUnitTest.class);

    @ArchTest
    ArchRule INTEGRATION_TEST_CLASSES_SHOULD_BE_PACKAGE_PRIVATE = classes().that()
        .haveSimpleNameEndingWith(INTEGRATION_TEST_SUFFIX)
        .should()
        .notHaveModifier(JavaModifier.ABSTRACT)
        .andShould()
        .bePackagePrivate()
        .allowEmptyShould(true);

    @ArchTest
    ArchRule INTEGRATION_TEST_CLASSES_SHOULD_EXTEND_BASE_INTEGRATION_UNIT_TEST = classes().that()
        .haveSimpleNameEndingWith(INTEGRATION_TEST_SUFFIX)
        .should()
        .beAssignableTo(BaseIntegrationTest.class)
        .allowEmptyShould(true);

    @ArchTest
    ArchRule TEST_METHODS_SHOULD_BE_PACKAGE_PRIVATE = methods().that()
        .areAnnotatedWith(Test.class)
        .should()
        .bePackagePrivate()
        .allowEmptyShould(true);

    @ArchTest
    ArchRule INTEGRATION_TESTS_SHOULD_BE_AWAY_FROM_CORE_LAYER = classes().that()
        .areAssignableTo(BaseIntegrationTest.class)
        .should()
        .resideOutsideOfPackage(DOMAIN_PACKAGE)
        .allowEmptyShould(true);

    @ArchTest
    ArchRule UNIT_AND_INTEGRATION_TEST_METHODS_SHOULD_FOLLOW_NAMING_CONVENTION = methods().that()
        .areDeclaredInClassesThat()
        .haveSimpleNameEndingWith(TEST_SUFFIX)
        .and()
        .areAnnotatedWith(Test.class)
        .or()
        .areDeclaredInClassesThat()
        .haveSimpleNameEndingWith(INTEGRATION_TEST_SUFFIX)
        .and()
        .areAnnotatedWith(Test.class)
        .should(new TestMethodNamingArchCondition())
        .because("Test method names must follow the pattern: " +
                 "methodBeingTested_should{ExpectedBehavior}_when{Condition}");

    @ArchTest
    ArchRule TEST_CLASSES_SHOULD_ONLY_USE_ASSERTJ_ASSERTIONS = noClasses().that()
        .haveSimpleNameEndingWith(TEST_SUFFIX)
        .or()
        .haveSimpleNameEndingWith(INTEGRATION_TEST_SUFFIX)
        .should()
        .dependOnClassesThat()
        .haveFullyQualifiedName("org.junit.jupiter.api.Assertions")
        .because("assertions must only use AssertJ (org.assertj.core.api.Assertions");
}
