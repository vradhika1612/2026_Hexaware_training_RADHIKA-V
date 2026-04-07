package com.springboot.bookapp.controller;

import com.springboot.bookapp.dto.SignUpDto;
import com.springboot.bookapp.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    // POST /api/user/sign-up  — public, no token needed
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpDto dto) {
        userService.signUp(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}