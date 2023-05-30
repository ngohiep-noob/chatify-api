package com.resource.api.controllers.room;

import com.resource.api.controllers.HttpResponse;
import com.resource.api.controllers.room.dtos.CreateRoomRequest;
import com.resource.api.controllers.room.dtos.JoinRoomRequest;
import com.resource.api.services.RoomService;
import com.resource.api.services.intefaces.IRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping()
    public ResponseEntity<HttpResponse> CreateRoom(@RequestBody @Valid
                                                   CreateRoomRequest dto) {

        return ResponseEntity.ok(roomService.CreateRoom(dto));
    }
    @PostMapping("/add-users")
    public ResponseEntity<HttpResponse> AddUsers(@RequestBody @Valid
                                                     JoinRoomRequest dto) {
        return ResponseEntity.ok(roomService.JoinRoom(dto));
    }
}
