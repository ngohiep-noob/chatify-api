package com.resource.api.controllers.room.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CreateRoomRequest {
    @NotBlank
    String name;

    @NotBlank
    String desc;

    ArrayList<Long> memberIds;
}
