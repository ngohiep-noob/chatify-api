package com.resource.api.controllers.user.dto;

import com.resource.api.models.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDTO {
    private Long id;

    private String username;

    private String email;

    private String fullName;

    private Role role;

    private boolean isActive;

}
