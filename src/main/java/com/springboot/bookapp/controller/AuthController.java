package com.springboot.bookapp.controller;

import com.springboot.bookapp.utility.JwtUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final JwtUtility jwtUtility;

    // GET /api/auth/login
    @GetMapping("/login")
    public ResponseEntity<?> login(Principal principal) {
        String username = principal.getName();
        Map<String, String> map = new HashMap<>();
        map.put("token", jwtUtility.generateToken(username));
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
}