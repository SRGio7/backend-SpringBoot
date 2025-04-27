package com.regi.backend.controller;

import com.regi.backend.entity.User;
import com.regi.backend.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody User loginUser, HttpSession session) {
        Optional<User> userOpt = userService.findByName(loginUser.getName());

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(loginUser.getPassword())) {
            User user = userOpt.get();
            session.setAttribute("username", user.getName());
            session.setAttribute("role", user.getRole());

            return ResponseEntity.ok("Login berhasil! ROLE:" + user.getRole());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username atau password salah.");
        }
    }
}
