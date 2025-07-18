package beto.projects.ipdbuddyapiv2.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // Allowed origins
        corsConfiguration.setAllowedOriginPatterns(List.of(
                "http://localhost:3003",
                "https://ipd-buddy.netlify.app"
        ));

        //Allowed methods
        corsConfiguration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        //Allowed headers
        corsConfiguration.setAllowedHeaders(List.of(
        "*"
        ));

        //Allowed credentials
        corsConfiguration.setAllowCredentials(true);

        //Register config for all paths
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }


}
