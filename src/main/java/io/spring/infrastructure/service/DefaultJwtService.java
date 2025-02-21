package io.spring.infrastructure.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.spring.core.service.JwtService;
import io.spring.core.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Component
public class DefaultJwtService implements JwtService {
    private final Key key;
    private final int sessionTime;

    public DefaultJwtService(@Value("${jwt.secret}") String secret,
                           @Value("${jwt.sessionTime}") int sessionTime) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        // Ensure key is at least 512 bits for HS512
        byte[] paddedKey = new byte[64]; // 64 bytes = 512 bits
        System.arraycopy(keyBytes, 0, paddedKey, 0, Math.min(keyBytes.length, paddedKey.length));
        this.key = Keys.hmacShaKeyFor(paddedKey);
        this.sessionTime = sessionTime;
    }

    @Override
    public String toToken(User user) {
        return Jwts.builder()
            .setSubject(user.getId())
            .setExpiration(expireTimeFromNow())
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    @Override
    public Optional<String> getSubFromToken(String token) {
        try {
            return Optional.ofNullable(
                Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject()
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Date expireTimeFromNow() {
        return new Date(System.currentTimeMillis() + sessionTime * 1000);
    }
}
