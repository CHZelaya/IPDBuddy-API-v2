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

    private static final String[] WHITELISTED_PUBLIC_API_ENDPOINTS = { "/user", "/user/login", "/user/refresh-token" };

//    private static final String[] WHITELISTED_AUTHENTICATED_API_ENDPOINTS = {
//            "/api/v1/contractor/me",
//            "/api/v1/job/submit",
//            "/api/v1/job/jobs",
//    // Future:
//    // "/api/v1/job/jobs/*"
//    };

    private final TokenAuthenticationFilter tokenAuthenticationFilter;

// standard constructor
    public SecurityConfig(TokenAuthenticationFilter tokenAuthenticationFilter) {
        this.tokenAuthenticationFilter = tokenAuthenticationFilter;
    }

    //  Add this to prevent fallback to default in-memory auth
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain configure(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(csrf -> csrf.disable())  // 🛑 Disable CSRF for API usage
                .authorizeHttpRequests(authManager -> {

                    authManager.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    logRule("OPTIONS", "/**", "Permit All Preflight Requests");

                    authManager.requestMatchers(HttpMethod.POST, WHITELISTED_PUBLIC_API_ENDPOINTS).permitAll();
                    logRule("POST", WHITELISTED_PUBLIC_API_ENDPOINTS, "Permit All");

                    authManager.requestMatchers(HttpMethod.POST, "/api/v1/job/submit").authenticated();
                    logRule("POST", "/api/v1/job/submit", "Authenticated");

                    authManager.requestMatchers(HttpMethod.GET, "/api/v1/job/jobs").authenticated();
                    logRule("GET", "/api/v1/job/jobs", "Authenticated");

                    authManager.requestMatchers(HttpMethod.GET, "/api/v1/contractor/me").authenticated();
                    logRule("GET", "/api/v1/contractor/me", "Authenticated");

                    authManager.requestMatchers(HttpMethod.PUT, "/api/v1/contractor/me").authenticated();
                    logRule("PUT", "/api/v1/contractor/me", "Authenticated");

                    authManager.anyRequest().authenticated();
                    logRule("Any", "Any", "Authenticated");
                })
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private void logRule(String method, Object endpoint, String permission) {
        System.out.println("SecurityConfig: Method [" + method + "], Endpoint [" + endpoint + "], Permission [" + permission + "]");
    }
}
