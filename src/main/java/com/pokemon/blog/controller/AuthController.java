package com.pokemon.blog.controller;

import com.pokemon.blog.entity.User;
import com.pokemon.blog.dto.response.LoginResponse;
import com.pokemon.blog.dto.request.LoginRequest;
import com.pokemon.blog.auth.JwtTokenProvider;
import com.pokemon.blog.exception.InvalidCredentialsException;
import com.pokemon.blog.exception.ResourceNotFoundException;
import com.pokemon.blog.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByUserName(loginRequest.getUserName())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy user: " + loginRequest.getUserName()
                ));

        if (!passwordEncoder.matches(loginRequest.getUserPassword(), user.getUserPassword())) {
            throw new InvalidCredentialsException("Mật khẩu không đúng");
        }

        String token = jwtTokenProvider.createToken(
                user.getId(),
                user.getUserName(),
                user.getRole().toString()
        );

        return new LoginResponse(
                token,
                user.getId(),
                user.getUserName(),
                user.getRole().toString()
        );
    }
}