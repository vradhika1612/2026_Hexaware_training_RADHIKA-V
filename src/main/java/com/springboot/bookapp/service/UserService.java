package com.springboot.bookapp.service;

import com.springboot.bookapp.dto.SignUpDto;
import com.springboot.bookapp.model.User;
import com.springboot.bookapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SignUpDto dto) {
        if (userRepository.findByUsername(dto.username()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }
        if (!dto.role().equals("AUTHOR") && !dto.role().equals("STUDENT")) {
            throw new RuntimeException("Role must be AUTHOR or STUDENT");
        }
        User user = User.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .role(dto.role())
                .build();
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}