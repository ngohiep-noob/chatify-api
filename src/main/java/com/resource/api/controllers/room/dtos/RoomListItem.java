package com.resource.api.controllers.room.dtos;

import com.resource.api.models.Message;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomListItem {
    private String name;
    private String desc;
    private Message lastMessage;
}


