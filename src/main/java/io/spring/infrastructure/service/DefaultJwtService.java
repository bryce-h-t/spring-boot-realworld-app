package io.spring.infrastructure.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.spring.core.service.JwtService;
import io.spring.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Component
public class DefaultJwtService implements JwtService {
    private final SecretKey key;
    private final int sessionTime;

    @Autowired
    public DefaultJwtService(@Value("${jwt.secret}") String secret,
                             @Value("${jwt.sessionTime}") int sessionTime) {
        // Generate a secure key for HS512
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        this.sessionTime = sessionTime;
    }

    @Override
    public String toToken(User user) {
        return Jwts.builder()
            .setSubject(user.getId())
            .setExpiration(expireTimeFromNow())
            .signWith(key)
            .compact();
    }

    @Override
    public Optional<String> getSubFromToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return Optional.ofNullable(claimsJws.getBody().getSubject());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Date expireTimeFromNow() {
        return new Date(System.currentTimeMillis() + sessionTime * 1000);
    }
}
