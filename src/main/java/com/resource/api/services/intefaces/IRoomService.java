package com.resource.api.services.intefaces;

import com.resource.api.controllers.HttpResponse;
import com.resource.api.controllers.room.dtos.CreateRoomRequest;
import com.resource.api.controllers.room.dtos.JoinRoomRequest;

public interface IRoomService {
    public HttpResponse CreateRoom(CreateRoomRequest dto);

    public HttpResponse JoinRoom(JoinRoomRequest dto);

}
