package com.resource.api.controllers.room.dtos;

import com.resource.api.controllers.chat.dtos.ChatDTO;
import com.resource.api.models.ChatEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class RoomDTO {
    Long id;
    String name;
    String desc;
    Long ownerId;
    String createdAt;
    List<ChatDTO> chats;
}
