package com.taxah.springdz7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private static final String LOGIN = "/login";

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("css/**", "/", LOGIN, "/logout").permitAll()
                        .requestMatchers("/public-data").authenticated()
                        .requestMatchers("/private-data").hasRole("ADMIN")
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

    @Bean
    UserDetailsManager inMemoryUserDetailsManager() {
        var user1 = User.withUsername("user")
                .password(bCrypt().encode("user")).roles("USER").build();
        var user2 = User.withUsername("admin")
                .password(bCrypt().encode("admin")).roles("USER", "ADMIN").build();
        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public BCryptPasswordEncoder bCrypt() {
        return new BCryptPasswordEncoder();
    }
}
