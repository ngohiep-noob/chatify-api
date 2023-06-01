package com.resource.api.controllers.room;

import com.resource.api.controllers.HttpResponse;
import com.resource.api.controllers.room.dtos.CreateRoomRequest;
import com.resource.api.controllers.room.dtos.JoinRoomRequest;
import com.resource.api.services.RoomService;
import com.resource.api.services.intefaces.IRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RoomController {
    private final RoomService roomService;

    @PostMapping()
    public ResponseEntity<HttpResponse> CreateRoom(@RequestBody @Valid
                                                   CreateRoomRequest dto) {
        HttpResponse response = roomService.CreateRoom(dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/add-users")
    public ResponseEntity<HttpResponse> AddUsers(@RequestBody @Valid
                                                 JoinRoomRequest dto) {
        HttpResponse response = roomService.JoinRoom(dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/chat-history/{roomId}")
    public ResponseEntity<HttpResponse> GetChatHistory(@PathVariable String roomId) {
        HttpResponse response = roomService.GetChatHistory(Long.parseLong(roomId));
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/user-list/{roomId}")
    public ResponseEntity<HttpResponse> GetUserList(@PathVariable String roomId) {
        HttpResponse response = roomService.GetUserList(Long.parseLong(roomId));
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
