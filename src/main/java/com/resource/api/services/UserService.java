package com.resource.api.services;

import com.resource.api.controllers.HttpResponse;
import com.resource.api.controllers.room.dtos.RoomListItem;
import com.resource.api.models.RoomEntity;
import com.resource.api.models.UserEntity;
import com.resource.api.repositories.UserRepository;
import com.resource.api.services.intefaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService, IUserService {
    private final UserRepository userRepository;

    public UserEntity loadUserByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public HttpResponse GetUserInfo(Long userId) {
        try {
            UserEntity user = userRepository.findById(userId).orElse(null);

            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            return new HttpResponse("get user info success", user.toDTO(), HttpStatus.OK);
        } catch (Exception e) {
            return new HttpResponse("Cannot get user information!", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public HttpResponse GetRoomListByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        Set<RoomEntity> roomList = user.getRooms();
        List<RoomListItem> roomListItemList = new ArrayList<>();
        for (RoomEntity room : roomList) {
            RoomListItem roomListItem = new RoomListItem();
            roomListItem.setName(room.getName());
        }

        return HttpResponse.builder()
                .message("Get user list successfully!")
                .data(roomListItemList)
                .status(HttpStatus.OK)
                .build();
    }
}
