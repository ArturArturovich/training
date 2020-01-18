package ru.offenso.config

import org.flywaydb.core.Flyway
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.data.domain.AuditorAware
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import ru.offenso.entities.User
import ru.offenso.extensions.securityPrincipal
import java.util.*
import java.util.concurrent.Executors

@EnableScheduling
@EnableAsync
@EnableCaching
@Configuration
class BeansConfig {

    // region Flyway

    @Bean
    fun flywayInitializer(flyway: Flyway) = FlywayMigrationInitializer(flyway) { }

    @Bean
    @DependsOn("transactionManager")
    fun delayedFlywayInitializer(flyway: Flyway) = FlywayMigrationInitializer(flyway, null)

    // endregion

    // region LocaleConfig

//    @Bean
//    fun localeResolver(): LocaleResolver = SessionLocaleResolver().apply {
//        setDefaultLocale(Locale.forLanguageTag("ru_RU"))
//    }

    // endregion

    @Bean
    fun taskScheduler(): TaskScheduler = ConcurrentTaskScheduler(Executors.newSingleThreadScheduledExecutor())

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun auditorProvider(): AuditorAware<User> = AuditorAware {
        securityPrincipal()?.run { Optional.of(user) } ?: Optional.empty()
    }
}