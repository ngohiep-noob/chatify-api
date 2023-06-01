package com.resource.api.services;

import com.resource.api.controllers.HttpResponse;
import com.resource.api.controllers.room.dtos.RoomListItem;
import com.resource.api.controllers.user.dto.UserDTO;
import com.resource.api.models.ChatEntity;
import com.resource.api.models.RoomEntity;
import com.resource.api.models.UserEntity;
import com.resource.api.repositories.ChatRepository;
import com.resource.api.repositories.RoomRepository;
import com.resource.api.repositories.UserRepository;
import com.resource.api.services.intefaces.IUserService;
import com.resource.api.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService, IUserService {
    private final UserRepository userRepository;

    private final ChatRepository chatRepository;

    private final RoomRepository roomRepository;

    private final SecurityUtils securityUtils;

    public UserEntity loadUserByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public HttpResponse GetProfile() {
        UserEntity user = securityUtils.getCurrentUser();
        return new HttpResponse("get user info success", user.toDTO(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public HttpResponse GetRooms() {
        try {
            UserEntity user = securityUtils.getCurrentUser();
            List<RoomEntity> roomList = roomRepository.findAllByUsersContains(user);
            List<RoomListItem> roomListItemList = new ArrayList<>();

            for (RoomEntity room : roomList) {
                RoomListItem roomListItem = new RoomListItem();
                ChatEntity latestMsg = chatRepository.getLastMessageOfRoom(room.getId());
                roomListItem.setName(room.getName());
                roomListItem.setLastMessage(latestMsg != null ? latestMsg.toDTO() : null);
                roomListItem.setId(room.getId());
                roomListItemList.add(roomListItem);
            }

            return HttpResponse.builder()
                    .message("Get room list successfully!")
                    .data(roomListItemList)
                    .status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new HttpResponse("Cannot get room list!", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public HttpResponse FindUserByName(String username) {
        try {
            ArrayList<UserEntity> users = userRepository.findByUsernameContainingIgnoreCase(username);
            System.out.println("Username: " + username);
            System.out.println(users);

            ArrayList<UserDTO> userDTOs = new ArrayList<>();

            UserEntity currentUser = securityUtils.getCurrentUser();
            for (UserEntity user : users) {
                if (Objects.equals(user.getId(), currentUser.getId())) continue;
                userDTOs.add(user.toDTO());
            }

            return HttpResponse.builder()
                    .message("Get user info successfully!")
                    .data(userDTOs)
                    .status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            return new HttpResponse("Cannot get user info!", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
