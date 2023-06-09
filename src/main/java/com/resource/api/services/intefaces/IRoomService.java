package com.resource.api.services.intefaces;

import com.resource.api.controllers.HttpResponse;
import com.resource.api.controllers.room.dtos.CreateRoomRequest;
import com.resource.api.controllers.room.dtos.JoinRoomRequest;
import org.springframework.http.ResponseEntity;

public interface IRoomService {
    public HttpResponse CreateRoom(CreateRoomRequest dto);

    public HttpResponse JoinRoom(JoinRoomRequest dto);

    public HttpResponse GetChatHistory(Long roomId);

    public HttpResponse GetUserList(Long roomId);
}
