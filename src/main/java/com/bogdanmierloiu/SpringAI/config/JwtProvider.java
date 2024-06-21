package com.bogdanmierloiu.SpringAI.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Component
public class JwtProvider {

    @Value("${spring.jwt.secret}")
    private String jwtSecret;

    private SecretKey key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication authentication) {
        String roles = extractAuthorities(authentication.getAuthorities());
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuedAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
                .expiration(Date.from(LocalDateTime.now().plusMinutes(60).toInstant(ZoneOffset.UTC)))
                .issuer("internal-authentication")
                .subject(authentication.getName())
                .claim("authorities", roles)
                .claim("email", authentication.getName())
                .signWith(key)
                .compact();
    }

    private static String extractAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            auths.add(authority.getAuthority());
        }
        return String.join(",", auths);
    }

}
