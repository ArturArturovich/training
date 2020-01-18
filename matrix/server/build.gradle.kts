import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion: String by project
val springBootVersion: String by project
val springCloudVersion: String by project
val kotlinCoroutinesVersion: String by project
val kotlinSerializationVersion: String by project
val kofuVersion: String by project
val poiVersion: String by project

plugins {
    kotlin("jvm")
    kotlin("plugin.jpa")
    kotlin("plugin.spring")
    id("kotlinx-serialization")
    id("org.springframework.boot")
    id("io.spring.dependency-management") version "1.0.7.RELEASE"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + arrayOf(
            "-Xjsr305=strict",
            "-Xjvm-default=enable",
            "-Xuse-experimental=kotlinx.coroutines.FlowPreview",
            "-Xuse-experimental=kotlinx.serialization.UnstableDefault",
            "-Xuse-experimental=kotlinx.serialization.ImplicitReflectionSerializer"
        )
    }
    dependsOn("processResources")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    testImplementation(project(":server"))
    implementation("org.mozilla:rhino:1.7.11")

    // region Kotlin

    fun kotlinx(module: String, version: String): Any = "org.jetbrains.kotlinx:kotlinx-$module:$version"

    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation(kotlinx("coroutines-core", kotlinCoroutinesVersion))
    implementation(kotlinx("coroutines-reactor", kotlinCoroutinesVersion))
    implementation(kotlinx("serialization-runtime", kotlinSerializationVersion))

    // endregion

    // region Spring Boot

    fun starter(module: String, version: String = springBootVersion): Any = "org.springframework.boot:spring-boot-starter-$module:$version"

    implementation(starter("data-jpa"))
    implementation(starter("security"))
    implementation(starter("thymeleaf"))
    implementation(starter("webflux"))
    implementation(starter("mail"))
    implementation(starter("websocket"))
//    implementation("org.springframework.fu:spring-fu-kofu:$kofuVersion")
    runtime("org.springframework.boot:spring-boot-devtools:$springBootVersion")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity5")
    implementation("org.thymeleaf.extras:thymeleaf-extras-java8time")
    implementation("org.springframework.cloud:spring-cloud-starter-oauth2")

    // endregion

    // region bower

    fun bower(module: String, version: String = "+") = implementation("org.webjars.bower:$module:$version")

    implementation("org.webjars:webjars-locator-core")
    bower("bootstrap")
    bower("bootstrap-select", "1.13.1")
    bower("jquery")
    bower("popper.js")
    bower("bootstrap-datepicker")
    bower("moment")
    bower("tempusdominus-bootstrap-4")
    bower("font-awesome")
    bower("datatables")

    // endregion

    // region tests

    testImplementation(starter("test"))
    testImplementation("org.springframework.security:spring-security-test")

    // endregion

    // region db

    runtime("mysql:mysql-connector-java")
    testRuntime("com.h2database:h2")
    implementation("org.flywaydb:flyway-core:5.2.4")

    // endregion

    // region apache poi

    implementation("org.apache.poi:poi:$poiVersion")
    implementation("org.apache.poi:poi-ooxml:$poiVersion")

    // endregion

    implementation("com.ibm.icu:icu4j:61.1")
}

configure<DependencyManagementExtension> {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}