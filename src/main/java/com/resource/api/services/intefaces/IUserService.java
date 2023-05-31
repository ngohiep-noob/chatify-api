package com.resource.api.services.intefaces;

import com.resource.api.controllers.HttpResponse;

public interface IUserService {
    public HttpResponse GetProfile();
    public HttpResponse GetRooms();

}
