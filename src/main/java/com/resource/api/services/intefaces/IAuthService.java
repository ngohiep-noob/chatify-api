package com.resource.api.services.intefaces;

import com.resource.api.controllers.auth.dtos.AuthenticationRequest;
import com.resource.api.controllers.auth.dtos.AuthenticationResponse;
import com.resource.api.controllers.auth.dtos.RegisterRequest;

public interface IAuthService {
    public AuthenticationResponse register(RegisterRequest req);

    public AuthenticationResponse login(AuthenticationRequest req);
}
