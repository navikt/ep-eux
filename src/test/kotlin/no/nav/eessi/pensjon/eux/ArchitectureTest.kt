package no.nav.eessi.pensjon.eux

import com.tngtech.archunit.base.DescribedPredicate
import com.tngtech.archunit.core.domain.JavaClass
import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import com.tngtech.archunit.library.Architectures.layeredArchitecture
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class ArchitectureTest {

    companion object {

        @JvmStatic
        private val root = "no.nav.eessi.pensjon.eux.model"

        @JvmStatic
        lateinit var allClasses: JavaClasses

        @JvmStatic
        lateinit var productionClasses: JavaClasses

        @JvmStatic
        lateinit var testClasses: JavaClasses

        @BeforeAll @JvmStatic
        fun `extract classes`() {
            allClasses = ClassFileImporter().importPackages(root)

            println("Validating size of allClasses, currently: ${allClasses.size}")
            assertTrue(allClasses.size > 200, "Sanity check on no. of classes to analyze")
            assertTrue(allClasses.size < 500, "Sanity check on no. of classes to analyze")

            productionClasses = ClassFileImporter()
                    .withImportOption(ImportOption.DoNotIncludeTests())
                    .withImportOption(ImportOption.DoNotIncludeJars())
                    .importPackages(root)
            println("Validating size of productionClass, currently: ${productionClasses.size}")
            assertTrue(productionClasses.size > 150, "Sanity check on no. of classes to analyze")
            assertTrue(productionClasses.size < 500, "Sanity check on no. of classes to analyze")

            testClasses = ClassFileImporter()
                    .withImportOption{ !ImportOption.DoNotIncludeTests().includes(it) }
                    .importPackages(root)

            println("Validating size of testClasses, currently: ${testClasses.size}")
            assertTrue(testClasses.size > 10, "Sanity check on no. of classes to analyze")
            assertTrue(testClasses.size < 500, "Sanity check on no. of classes to analyze")
        }
    }

    @Test
    fun `check architecture in detail`() {

        // components
        val buc = "buc"
        val document = "document"
        val sed = "sed"


        val packages: Map<String, String> = mapOf(
                "$root.buc.." to buc,
                "$root.document.." to document,
                "$root.sed.." to sed,
            )

        // packages in each component - default is the package with the component name
        fun packagesFor(layer: String) = packages.entries.filter { it.value == layer }.map { it.key }.toTypedArray()

        // mentally replace the word "layer" with "component":
        layeredArchitecture()
                .layer(buc).definedBy(*packagesFor(buc))
                .layer(document).definedBy(*packagesFor(document))
                .layer(sed).definedBy(*packagesFor(sed))

                .whereLayer(buc).mayNotBeAccessedByAnyLayer()
                .whereLayer(document).mayNotBeAccessedByAnyLayer()
                .whereLayer(sed).mayNotBeAccessedByAnyLayer()

                .check(allClasses)
    }

    @Test
    fun `main layers check`() {
        val buc = "BUC"
        val sed = "SED"
        val document = "Dcoument"
        layeredArchitecture()
                .layer(buc).definedBy("$root.buc..")
                .layer(sed).definedBy("$root.sed..")
                .layer(document).definedBy("$root.document..")
                .whereLayer(buc).mayNotBeAccessedByAnyLayer()
                .whereLayer(sed).mayNotBeAccessedByAnyLayer()
                .whereLayer(document).mayNotBeAccessedByAnyLayer()
                .check(allClasses)
    }

    @Test
    fun `no cycles on top level`() {
        slices()
                .matching("$root.(*)..")
                .should().beFreeOfCycles()
                .check(allClasses)
    }

    @Test
    fun `no cycles on any level for production classes`() {
        slices()
                .matching("$root..(*)")
                .should().beFreeOfCycles()
                .check(productionClasses)
    }

    @Test
    fun `tests should assert, not log`() {
        noClasses().that().haveNameNotMatching(".*\\.logging\\..*") // we allow using slf4j in the logging-package
                .should().dependOnClassesThat().resideInAPackage("org.slf4j..")
                .because("Test should assert, not log; after you made your test the logs will not be checked")
                .check(testClasses)
    }


    @Test
    fun `No test classes should use inheritance`() {
        class TestSupportClasses:DescribedPredicate<JavaClass>("test support classes") {
            override fun apply(input: JavaClass?) = input != null &&
                    (!input.simpleName.endsWith("Test") &&
                            (!input.simpleName.endsWith("Tests")
                                    && input.name != "java.lang.Object"))
        }

        noClasses().that().haveSimpleNameEndingWith("Test").or().haveSimpleNameEndingWith("Tests")
                .should().beAssignableTo(TestSupportClasses())
                .because("it is hard to understand the logic of tests that inherit from other classes.")
                .check(testClasses)
    }
}
