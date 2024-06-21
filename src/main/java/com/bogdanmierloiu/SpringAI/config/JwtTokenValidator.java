package com.bogdanmierloiu.SpringAI.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JwtTokenValidator extends OncePerRequestFilter {

    private final SecretKey key;

    public JwtTokenValidator(String jwtSecret) {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        Optional<String> jwt = extractJwtFromHeader(request);
        if (jwt.isPresent()) {
            try {
                Map<String, String> claims = getClaims(jwt.get(), List.of("authorities", "email"));
                List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(claims.get("authorities"));
                Authentication authentication = new UsernamePasswordAuthenticationToken(claims.get("email"), null, auth);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid token", e);
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return !requestURI.startsWith("/api");
    }

    private Optional<String> extractJwtFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return Optional.ofNullable(authorizationHeader)
                .filter(j -> j.startsWith("Bearer "))
                .map(j -> j.substring(7));
    }

    public Map<String, String> getClaims(String jwt, List<String> claimsToReturn) {
        Map<String, String> claims = new HashMap<>();
        try {
            Claims parsedClaims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();
            for (var claim : claimsToReturn) {
                claims.put(claim, String.valueOf(parsedClaims.get(claim)));
            }
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
        return claims;
    }

}
