package com.junho.productmgnt.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FcmConfig {
    String SERVICE_ACCOUNT_KEY = "firebase/serviceAccountKey.json";

    @Bean
    FirebaseMessaging firebaseMessagingEntryPoint() throws IOException {
        ClassPathResource serviceAccount = new ClassPathResource(SERVICE_ACCOUNT_KEY);
        InputStream serviceAccountStream = serviceAccount.getInputStream();

        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
            .build();

        FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);

        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
