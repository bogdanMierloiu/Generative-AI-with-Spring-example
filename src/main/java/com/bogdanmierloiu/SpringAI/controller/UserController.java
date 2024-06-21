package com.bogdanmierloiu.SpringAI.controller;

import com.bogdanmierloiu.SpringAI.entity.User;
import com.bogdanmierloiu.SpringAI.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/profile")
public class UserController {

    private final UserRepo userRepo;

    @GetMapping
    public ResponseEntity<User> getUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.toString();
        User user = userRepo.findByEmail(email).get();
        return ResponseEntity.ok(user);
    }

}
