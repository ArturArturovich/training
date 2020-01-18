package ru.offenso

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import ru.offenso.properties.AppProperties
import java.util.*

@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
class Application

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"))
    runApplication<Application>(*args)
}
