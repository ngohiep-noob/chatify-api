package com.resource.api.controllers;

import com.resource.api.controllers.dtos.AuthenticationRequest;
import com.resource.api.controllers.dtos.AuthenticationResponse;
import com.resource.api.controllers.dtos.RegisterRequest;
import com.resource.api.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class Auth {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid
            @RequestBody RegisterRequest registerRequest
    ) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        return ResponseEntity.ok(authService.login(authenticationRequest));
    }

    @GetMapping("/protected")
    public ResponseEntity<HashMap<String, String>> test() {
        HashMap<String, String> res = new HashMap<>();
        res.put("message", "Test successful");
        return ResponseEntity.ok(res);
    }
}
