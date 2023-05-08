package com.resource.api.services;

import com.resource.api.config.JwtService;
import com.resource.api.controllers.dtos.AuthenticationRequest;
import com.resource.api.controllers.dtos.AuthenticationResponse;
import com.resource.api.controllers.dtos.RegisterRequest;
import com.resource.api.models.Role;
import com.resource.api.models.UserEntity;
import com.resource.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest req) {
        try {
            UserEntity userEntity = UserEntity.builder()
                    .username(req.getUsername())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .email(req.getEmail())
                    .role(Role.USER)
                    .fullName(req.getFullname())
                    .build();
            userRepository.save(userEntity);
            var jwtToken = jwtService.generateToken(userEntity);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public AuthenticationResponse login(AuthenticationRequest req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        var user = userRepository.findByUsername(req.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
