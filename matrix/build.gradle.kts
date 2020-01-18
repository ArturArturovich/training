plugins {
    kotlin("plugin.spring") apply false
}

allprojects {
    group = "ru.offenso"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
        jcenter()
        maven("https://repo.spring.io/milestone/")
        maven("http://nexus.dev.internal/repository/maven")
    }
}
