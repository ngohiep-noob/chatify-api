package com.resource.api.services;

import com.resource.api.config.JwtService;
import com.resource.api.controllers.auth.dtos.AuthenticationRequest;
import com.resource.api.controllers.auth.dtos.AuthenticationResponse;
import com.resource.api.controllers.auth.dtos.RegisterRequest;
import com.resource.api.models.Role;
import com.resource.api.models.UserEntity;
import com.resource.api.repositories.UserRepository;
import com.resource.api.services.intefaces.IAuthService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest req) {
        try {
            UserEntity existingUser = userRepository
                    .findByUsername(req.getUsername())
                    .orElse(null);

            if (existingUser != null)
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");


            UserEntity userEntity = UserEntity.builder()
                    .username(req.getUsername())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .email(req.getEmail())
                    .role(Role.USER)
                    .fullName(req.getFullname())
                    .isActive(true)
                    .build();
            userRepository.save(userEntity);
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("role", Role.USER);
            var jwtToken = jwtService.generateToken(claims, userEntity);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public AuthenticationResponse login(AuthenticationRequest req) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

            var user = userRepository
                    .findByUsername(req.getUsername())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

            HashMap<String, Object> claims = new HashMap<>();
            claims.put("role", user.getRole());
            var jwtToken = jwtService.generateToken(claims, user);
            System.out.println("Generated token: " + jwtToken);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (Exception e) {
            if (e instanceof ResponseStatusException)
                throw e;

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
