package io.spring.infrastructure.service;

import io.spring.core.service.JwtService;
import io.spring.core.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultJwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        String secret = "UDVOVnJXazlWVVJIWXBManpUelFIOUxrRXVvS3Z2aGs2SEp4VUZSeW9HUFpxZ3dqSzNZUWQ3YlpqN0VxVEs0Sw==";
        jwtService = new DefaultJwtService(secret, 1); // 1 second expiration for tests
    }

    @Test
    public void should_generate_and_parse_token() {
        User user = new User("email@email.com", "username", "123", "", "");
        String token = jwtService.toToken(user);
        assertNotNull(token);
        Optional<String> optional = jwtService.getSubFromToken(token);
        assertTrue(optional.isPresent());
        assertEquals(optional.get(), user.getId());
    }

    @Test
    public void should_get_null_with_wrong_jwt() {
        Optional<String> optional = jwtService.getSubFromToken("123");
        assertFalse(optional.isPresent());
    }

    @Test
    public void should_get_null_with_expired_jwt() {
        User user = new User("email@email.com", "username", "123", "", "");
        String token = jwtService.toToken(user);
        // Wait for token to expire
        try {
            Thread.sleep(1100); // Wait just over 1 second
        } catch (InterruptedException e) {
            // ignore
        }
        assertFalse(jwtService.getSubFromToken(token).isPresent());
    }
}
