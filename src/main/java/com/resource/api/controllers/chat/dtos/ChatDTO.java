package com.resource.api.controllers.chat.dtos;

import com.resource.api.controllers.user.dto.UserDTO;
import com.resource.api.models.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ChatDTO {
    String message;
    UserDTO user;
    LocalDateTime createdAt;
}
