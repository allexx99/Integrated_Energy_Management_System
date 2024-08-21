package com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Configuration
public class JWTConfig {

    @Value("${application.security.jwt.secret-key}")
    private String secretKeyString;

    @Bean
    public SecretKey jwtSecretKey() {
        byte[] decodedKey = java.util.Base64.getDecoder().decode(secretKeyString);
        return Keys.hmacShaKeyFor(decodedKey);
    }
}
