package com.bogdanmierloiu.SpringAI.controller;

import com.bogdanmierloiu.SpringAI.config.JwtProvider;
import com.bogdanmierloiu.SpringAI.config.UserDetailsServiceConfig;
import com.bogdanmierloiu.SpringAI.dto.AuthResponse;
import com.bogdanmierloiu.SpringAI.dto.LoginRequest;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UserDetailsServiceConfig userDetailsServiceConfig;

    private final JwtProvider jwtProvider;


    @PostMapping("auth/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = userDetailsServiceConfig.authenticate(loginRequest.username(), loginRequest.password());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = AuthResponse.builder()
                .message("Success")
                .jwt(token)
                .status(true)
                .build();

        return ResponseEntity.ok().body(authResponse);
    }

    @PostMapping("metamask/login")
    public String loginWithMetaMask() {
        return "Login with MetaMask";
    }

}
