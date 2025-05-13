package beto.projects.ipdbuddyapiv2.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class FirebaseConfig {

    private static final Logger log = LoggerFactory.getLogger(FirebaseConfig.class);

    @Value("classpath:firebase-service-account.json")
    private Resource privateKey;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        log.info("Initializing FirebaseApp...");

        //? Grabbing the credentials from the json file.
        try (InputStream credentials = new ByteArrayInputStream(privateKey.getContentAsByteArray());) {

            FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(credentials))
                    .setProjectId("ipd-buddy")
                    .build();
            log.info("Firebase initialized successfully with project ID: {}", firebaseOptions.getProjectId());
            return FirebaseApp.initializeApp(firebaseOptions);

        } catch (IOException e) {
            log.error("Failed to initialize Firebase App", e);
            throw e;
        }
    }

    @Bean
    public FirebaseAuth firebaseAuth(FirebaseApp firebaseApp) {
        log.info("FirebaseAuth created successfully");
        return FirebaseAuth.getInstance(firebaseApp);
    }
}
