package ru.offenso.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository
import ru.offenso.repositories.PersistentLoginTokenRepository
import ru.offenso.services.UserDetailsService
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val dataSource: DataSource,
    private val passwordEncoder: PasswordEncoder,
    private val tokenRepo: PersistentLoginTokenRepository
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http
            .authorizeRequests()
                .antMatchers("/", "/login", "/forgot/**").permitAll()
                .antMatchers("/webjars/**", "/**/*.css", "/**/*.js", "/favicon.ico").permitAll()
                .antMatchers("/switchUser").access("hasAnyRole('ADMIN', 'ROLE_PREVIOUS_ADMINISTRATOR')")
                .anyRequest().authenticated()
        .and()
            .exceptionHandling().accessDeniedPage("/login?error")
        .and()
            .formLogin()
                .loginProcessingUrl("/login")
                .failureUrl("/login?error")
                .loginPage("/login")
        .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("remember-me")
        .and()
            .rememberMe().tokenRepository(tokenRepo).tokenValiditySeconds(3600 * 24 * 7)
        .and()
            .csrf()
                .csrfTokenRepository(HttpSessionCsrfTokenRepository()) // or maybe CookieCsrfTokenRepository
                .ignoringAntMatchers("/api/**")
        // @formatter:on
    }

    @Bean
    override fun authenticationManagerBean() = super.authenticationManagerBean()!!

    override fun configure(auth: AuthenticationManagerBuilder) {
        // @formatter:off
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder)
        .and()
            .authenticationProvider(authenticationProvider())
            .jdbcAuthentication().dataSource(dataSource)
        // @formatter:on
    }

    @Bean
    fun switchUserFilter() = SwitchUserFilter().apply {
        setUserDetailsService(userDetailsService)
        setSwitchUserUrl("/impersonate")
        setSwitchFailureUrl("/switchUser")
        setTargetUrl("/")
    }

    @Bean
    fun authenticationProvider() = DaoAuthenticationProvider().apply {
        setUserDetailsService(userDetailsService)
        setPasswordEncoder(passwordEncoder)
    }
}