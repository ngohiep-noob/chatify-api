package com.resource.api.controllers.room.dtos;

import com.resource.api.controllers.chat.dtos.ChatDTO;
import com.resource.api.models.ChatEntity;
import com.resource.api.models.Message;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomListItem {
    private String name;
    private String desc;
    private ChatDTO lastMessage;
}


