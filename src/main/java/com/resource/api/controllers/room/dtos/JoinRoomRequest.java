package com.resource.api.controllers.room.dtos;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@Data
@Builder
public class JoinRoomRequest {
    private Long roomId;
    private ArrayList<String> usernames;
}
