package com.api.auth.service;

import com.api.auth.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @BeforeEach
    public void setup() {
        // Set values for the @Value fields using ReflectionTestUtils
        ReflectionTestUtils.setField(jwtService, "secretKey", "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 86400000); // Example expiration time in milliseconds
    }

    @Test
    public void testGenerateAndExtractToken() {
        // Create a sample user
        User user = new User();
        user.setEmail("test@example.com");

        // Generate a token
        String token = jwtService.generateToken(user);

        // Extract claim and verify
        String email = jwtService.extractClaim(token, Claims::getSubject);
        assertEquals("test@example.com", email);
    }

}

