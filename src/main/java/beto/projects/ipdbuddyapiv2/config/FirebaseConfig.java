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
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class FirebaseConfig {

    private static final Logger log = LoggerFactory.getLogger(FirebaseConfig.class);


    //@Value("classpath:firebase-service-account.json")
    @Value("${FIREBASE_SERVICE_ACCOUNT_BASE64}")
    private String serviceAccountBase64;

    // ✅ Read from Environment Variable
    //String serviceAccountJson = System.getenv("FIREBASE_SERVICE_ACCOUNT");

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        log.info("Initializing FirebaseApp...");
        byte[] decodedBytes = Base64.getDecoder().decode(serviceAccountBase64);

        //? Grabbing the credentials from the json file.
        try (ByteArrayInputStream credentialsStream = new ByteArrayInputStream(decodedBytes)) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(credentialsStream))
                    .setProjectId("ipd-buddy")
                    .build();
            return FirebaseApp.initializeApp(options);
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
