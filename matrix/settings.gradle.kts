rootProject.name = "matrix"

include(":server")

val kotlinVersion: String by settings
val kotlinSerializationVersion: String by settings
val springBootVersion: String by settings

pluginManagement {
    repositories {
        mavenCentral()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
        maven("http://nexus.dev.internal/repository/maven")
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "kotlinx-serialization") {
                useModule("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
            }
            if (requested.id.id == "org.springframework.boot") {
                useModule("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
            }
            if (requested.id.namespace?.startsWith("org.jetbrains.kotlin") == true) {
                useVersion(kotlinVersion)
            }
        }
    }
}

buildCache {
    val isCiServer = System.getenv().containsKey("CI")

    local {
        isEnabled = !isCiServer
    }
    remote<HttpBuildCache> {
        url = uri("https://gradle-cache.git.dev.internal/cache/")
        isAllowUntrustedServer = true
        isEnabled = true
        isPush = isCiServer
        credentials {
            username = "ci"
            password = "T22QFyRhbSGqjWLq"
        }
    }
}
