package com.taxah.springdz7.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * The SecurityConfig class configures security settings for the web application using Spring Security.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private static final String LOGIN = "/login";

    /**
     * Configures the security filter chain for HTTP requests.
     *
     * @param http The HttpSecurity object to configure security settings.
     * @return The configured SecurityFilterChain.
     * @throws Exception If configuration fails.
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("css/**", "/").permitAll()
                        .requestMatchers("/public-data").authenticated()
                        .requestMatchers("/private-data").hasRole(Roles.ADMIN.get())
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage(LOGIN)
                        .failureUrl(LOGIN + "?error=true")
                        .defaultSuccessUrl("/")
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl(LOGIN)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .exceptionHandling(exception -> exception.accessDeniedPage(LOGIN));
        return http.build();
    }

    /**
     * Creates an in-memory user details manager with predefined user accounts.
     *
     * @return The configured UserDetailsManager.
     */
    @Bean
    UserDetailsManager inMemoryUserDetailsManager() {
        var user1 = User.withUsername("user")
                .password(bCrypt().encode("user"))
                .roles(Roles.USER.get()).build();
        var user2 = User.withUsername("admin")
                .password(bCrypt().encode("admin"))
                .roles(Roles.USER.get(), Roles.ADMIN.get()).build();
        return new InMemoryUserDetailsManager(user1, user2);
    }

    /**
     * Creates a BCrypt password encoder.
     *
     * @return The BCryptPasswordEncoder instance.
     */
    @Bean
    public BCryptPasswordEncoder bCrypt() {
        return new BCryptPasswordEncoder();
    }
}
