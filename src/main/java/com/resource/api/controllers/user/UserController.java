package com.resource.api.controllers.user;

import com.resource.api.controllers.HttpResponse;
import com.resource.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<HttpResponse> GetProfile() {
        HttpResponse response = userService.GetProfile();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/rooms")
    public ResponseEntity<HttpResponse> GetRooms() {
        HttpResponse response = userService.GetRooms();
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
