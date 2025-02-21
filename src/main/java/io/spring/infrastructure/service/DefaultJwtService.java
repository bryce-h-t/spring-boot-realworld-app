package io.spring.infrastructure.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.spring.core.service.JwtService;
import io.spring.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class DefaultJwtService implements JwtService {
    private String secret;
    private int sessionTime;

    @Autowired
    public DefaultJwtService(@Value("${jwt.secret}") String secret,
                             @Value("${jwt.sessionTime}") int sessionTime) {
        this.secret = secret;
        this.sessionTime = sessionTime;
    }

    @Override
    public String toToken(User user) {
        byte[] keyBytes = secret.getBytes();
        return Jwts.builder()
            .setSubject(user.getId())
            .setExpiration(expireTimeFromNow())
            .signWith(Jwts.SIG.HS512.key().hmacShaKeyFor(keyBytes))
            .compact();
    }

    @Override
    public Optional<String> getSubFromToken(String token) {
        try {
            byte[] keyBytes = secret.getBytes();
            var jwt = Jwts.parser()
                .verifyWith(Jwts.SIG.HS512.key().hmacShaKeyFor(keyBytes))
                .build()
                .parseSignedClaims(token);
            return Optional.ofNullable(jwt.getPayload().getSubject());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Date expireTimeFromNow() {
        return new Date(System.currentTimeMillis() + sessionTime * 1000);
    }
}
