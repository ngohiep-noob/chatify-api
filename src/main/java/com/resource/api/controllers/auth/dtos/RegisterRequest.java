package com.resource.api.controllers.auth.dtos;

import com.resource.api.models.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "Username is required")
    String username;

    @NotBlank(message = "Full name is required")
    String fullname;

    @NotBlank(message = "Password is required")
    String password;

    @NotBlank(message = "Email is required")
    String email;

    @Enumerated(EnumType.STRING)
    Role role;
}
