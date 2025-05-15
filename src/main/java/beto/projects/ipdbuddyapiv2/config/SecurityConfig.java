package beto.projects.ipdbuddyapiv2.config;

import beto.projects.ipdbuddyapiv2.security.TokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private static final String[] WHITELISTED_API_ENDPOINTS = { "/user", "/user/login", "/user/refresh-token" };

    private final TokenAuthenticationFilter tokenAuthenticationFilter;

// standard constructor
    public SecurityConfig(TokenAuthenticationFilter tokenAuthenticationFilter) {
        this.tokenAuthenticationFilter = tokenAuthenticationFilter;
    }

    // ✅ Add this to prevent fallback to default in-memory auth
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain configure(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(authManager -> {
                    authManager.requestMatchers(HttpMethod.POST, WHITELISTED_API_ENDPOINTS)
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
