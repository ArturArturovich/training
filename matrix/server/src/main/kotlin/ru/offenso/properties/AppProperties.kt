package ru.offenso.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app", ignoreUnknownFields = true)
class AppProperties {
    val email = Email()
    val paths = Paths()

    class Email {
        var from: String = "matrix"
    }

    class Paths {
        var images: String = "./images"
        var files: String = "./files"
    }
}