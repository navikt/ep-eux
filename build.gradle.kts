import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
    `java-library`
    id("net.researchgate.release") version "2.8.1"
    `maven-publish`
    id("com.adarshr.test-logger") version "3.1.0"
    id("org.jetbrains.kotlin.plugin.spring") version "1.6.10"
    id("com.github.ben-manes.versions") version "0.39.0"
    id("se.patrikerdes.use-latest-versions") version "0.2.18"
    id("org.owasp.dependencycheck") version "6.5.0.1"
}

group = "no.nav.eessi.pensjon"

java {
    withJavadocJar()
    withSourcesJar()
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
    kotlinOptions.jvmTarget = "11"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val springVersion by extra("5.3.13")
//val cxfVersion by extra("3.3.6")

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation(kotlin("stdlib", "1.6.10"))

    implementation("io.micrometer:micrometer-registry-prometheus:1.8.1")
    implementation("no.nav.eessi.pensjon:ep-security-sts:0.0.20")
    implementation("no.nav.eessi.pensjon:ep-metrics:0.4.10")

    // Spring
    implementation("org.springframework:spring-web:$springVersion")
    implementation("org.springframework.retry:spring-retry:1.3.1")

    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("no.nav.eessi.pensjon:ep-logging:1.0.14")
    implementation("org.slf4j:slf4j-api:1.7.32")
    //Jackson json
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.0")

    // Test
    testImplementation("org.skyscreamer:jsonassert:1.5.0")
    testImplementation("org.springframework:spring-test:$springVersion")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("io.mockk:mockk:1.12.1")

    // Architecture tests
    testImplementation ("com.tngtech.archunit:archunit:0.22.0")
}

// https://github.com/researchgate/gradle-release
release {
    newVersionCommitMessage = "[Release Plugin] - next version commit: "
    tagTemplate = "release-\${version}"
}

// https://help.github.com/en/actions/language-and-framework-guides/publishing-java-packages-with-gradle#publishing-packages-to-github-packages
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/navikt/${rootProject.name}")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

repositories {
    mavenCentral()

    val token = System.getenv("GITHUB_TOKEN") ?: project.findProperty("gpr.key")
    ?: throw NullPointerException("Missing token, you have to set GITHUB_TOKEN or gpr.key, see README")

    maven("https://maven.pkg.github.com/navikt/maven-release") {
        credentials {
            username = "token"
            password = token as String
        }
    }
}

/* https://github.com/ben-manes/gradle-versions-plugin */
tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        listOf("alpha", "beta", "rc", "cr", "m", "preview", "pr2")
                .any { qualifier -> """(?i).*[.-]${qualifier}[.\d-]*""".toRegex().matches(candidate.version) }
    }
    revision = "release"
}
