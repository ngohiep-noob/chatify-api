package com.resource.api.controllers.room.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.Set;

@Data
public class CreateRoomRequest {
    @NotBlank
    String name;

    @NotBlank
    String desc;

    @NotBlank
    ArrayList<Long> membersId;

    @NotBlank
    Long creatorId;
}
