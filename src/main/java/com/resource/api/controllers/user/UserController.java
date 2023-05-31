package com.resource.api.controllers.user;

import com.resource.api.controllers.HttpResponse;
import com.resource.api.models.UserEntity;
import com.resource.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<HttpResponse> GetUserInfo(@PathVariable(value = "userId")
                                                    String userId) {
        return ResponseEntity.ok(userService.GetUserInfo(Long.parseLong(userId)));
    }

    public HttpResponse GetRoomListByUserId(Long userId) {
        return null;
    }

    public HttpResponse GetFriendListByUserId(Long userId) {
        return null;
    }
}
