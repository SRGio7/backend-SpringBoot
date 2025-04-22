package com.regi.backend.controller;

import com.regi.backend.entity.User;
import com.regi.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody User loginUser) {
        boolean isValid = userService.validateLogin(loginUser.getName(), loginUser.getPassword());
        if (isValid) {
            return ResponseEntity.ok("Login berhasil!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username atau password salah.");
        }
    }
}
