package com.resource.api.services.intefaces;

import com.resource.api.controllers.HttpResponse;
import com.resource.api.controllers.room.dtos.RoomListItem;
import com.resource.api.models.UserEntity;

import java.util.ArrayList;

public interface IUserService {
    public HttpResponse GetUserInfo(Long userId);

    public HttpResponse GetRoomListByUserId(Long userId);

}
