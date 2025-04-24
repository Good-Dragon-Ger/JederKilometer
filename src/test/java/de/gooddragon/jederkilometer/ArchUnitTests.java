package de.gooddragon.jederkilometer;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;
import de.gooddragon.jederkilometer.domain.model.AggregateRoot;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packagesOf = JederKilometerApplication.class, importOptions = {ImportOption.DoNotIncludeTests.class})
public class ArchUnitTests {

    @ArchTest
    ArchRule onionTest = onionArchitecture()
            .domainModels("..domain.model..")
            .domainServices("..domain.service..")
            .applicationServices("..application.service..")
            .adapter("web", "..adapter.web..")
            .adapter("database", "..adapter.database..");

    @ArchTest
    ArchRule onlyControllersCanHaveControllerAnnotation = classes()
            .that()
            .areAnnotatedWith(Controller.class)
            .should()
            .resideInAPackage("..adapter.web.controller..")
            .as("Verstößt sonst gegen Onion-Architektur");

    @ArchTest
    ArchRule noClassesOutsideOfControllerPackageShouldAccessTheControllers = noClasses()
            .that()
            .resideOutsideOfPackage("..adapter.web.controller..")
            .should()
            .dependOnClassesThat()
            .resideInAPackage("..adapter.web.controller..")
            .as("Abhängigkeit würde einen Verstoß gegen Onion-Architektur darstellen");

    @ArchTest
    ArchRule noClassesShouldUseFieldInjection = GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION;

    @ArchTest
    ArchRule allClassesInApplicationServiceShouldBeAnnotatedWithService = classes()
            .that()
            .resideInAPackage("..application.service")
            .and()
            .resideOutsideOfPackages("..application.services.repository..")
            .should()
            .beAnnotatedWith(Service.class);

    @ArchTest
    ArchRule variablesInControllerShouldBePrivate = fields()
            .that()
            .areDeclaredInClassesThat()
            .areAnnotatedWith(Controller.class)
            .should()
            .bePrivate();
    @ArchTest
    ArchRule classesThatAreAnnotatedWithAggregateRootArePublic = classes()
            .that()
            .areAnnotatedWith(AggregateRoot.class)
            .should()
            .bePublic()
            .as("Macht keinen Sinn eine private Aggregateroot zu haben");
    @ArchTest
    ArchRule classesThatAreRepositoryImplementationShouldBeInRightPackage = classes()
            .that()
            .haveSimpleNameContaining("RepositoryImpl")
            .should()
            .resideInAPackage("..database.repository..");
    @ArchTest
    ArchRule classesThatAreConfigsShouldResideInRightPackage = classes()
            .that()
            .haveSimpleNameContaining("Config")
            .should()
            .resideInAPackage("..config..");

}
