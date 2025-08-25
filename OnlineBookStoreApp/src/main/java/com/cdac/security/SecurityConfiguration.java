package com.cdac.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@AllArgsConstructor
@Configuration
public class SecurityConfiguration {

    private final PasswordEncoder encoder;
    private final CustomJwtFilter customJwtFilter;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final CorsConfigurationSource corsConfigurationSource;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());
        http.cors(cors -> cors.configurationSource(corsConfigurationSource));

        http.authorizeHttpRequests(request ->

                request.requestMatchers("/swagger-ui/**", "/v**/api-docs/**"
                                , "/users/signin", "/users/signup", "/api/sample/**").permitAll()

                        .requestMatchers("/error").permitAll()

                        .requestMatchers(HttpMethod.OPTIONS).permitAll()

                        .requestMatchers(HttpMethod.GET, "/books").permitAll()

                        .requestMatchers(HttpMethod.GET, "/books/{id}").hasRole("CUSTOMER")

                        .requestMatchers(HttpMethod.POST, "/books/add-with-image").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/books/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/books/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/books/{id}").hasRole("ADMIN")
                        
                        .requestMatchers("/auth/forgot-password", "/auth/reset-password").permitAll()
                        
                        .anyRequest().authenticated());





        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling(ex ->
                ex.authenticationEntryPoint(jwtAuthEntryPoint));
        return http.build();
    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration mgr) throws Exception {
        return mgr.getAuthenticationManager();
    }

}
