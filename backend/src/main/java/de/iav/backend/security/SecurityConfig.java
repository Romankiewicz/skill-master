package de.iav.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(customizer -> {
                    customizer.requestMatchers(HttpMethod.GET, "").permitAll();
                    customizer.requestMatchers(HttpMethod.POST, "/api/students/**").authenticated();
                    customizer.requestMatchers(HttpMethod.PUT, "/api/students/**").authenticated();
                    customizer.requestMatchers(HttpMethod.GET, "/api/students/**").authenticated();
                    customizer.requestMatchers(HttpMethod.DELETE, "/api/students/**").authenticated();
                    customizer.requestMatchers(HttpMethod.GET, "/api/teachers").authenticated();
                    customizer.requestMatchers(HttpMethod.POST, "/api/teachers/**").authenticated();
                    customizer.requestMatchers(HttpMethod.PUT, "/api/teachers/**").authenticated();
                    customizer.requestMatchers(HttpMethod.GET, "/api/teachers/**").authenticated();
                    customizer.requestMatchers(HttpMethod.DELETE, "/api/teachers/**").authenticated();
                    customizer.requestMatchers(HttpMethod.POST, "/api/courses/**").authenticated();
                    customizer.requestMatchers(HttpMethod.PUT, "/api/courses/**").authenticated();
                    customizer.requestMatchers(HttpMethod.GET, "/api/courses/**").authenticated();
                    customizer.requestMatchers(HttpMethod.DELETE, "/api/courses/**").authenticated();
                    customizer.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                //.formLogin(AbstractHttpConfigurer::disable)
                //.logout(Customizer.withDefaults())
                .build();
    }
}
