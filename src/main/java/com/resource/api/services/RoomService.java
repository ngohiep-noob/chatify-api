package com.resource.api.services;

import com.resource.api.controllers.HttpResponse;
import com.resource.api.controllers.room.dtos.CreateRoomRequest;
import com.resource.api.controllers.room.dtos.JoinRoomRequest;
import com.resource.api.models.RoomEntity;
import com.resource.api.models.UserEntity;
import com.resource.api.repositories.RoomRepository;
import com.resource.api.repositories.UserRepository;
import com.resource.api.services.intefaces.IRoomService;
import com.resource.api.utils.SecurityUtils;
import jakarta.persistence.EntityManager;
import jakarta.transaction.RollbackException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.hibernate.annotations.Fetch;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {

    private final RoomRepository roomRepository;

    private final UserRepository userRepository;

    private final SecurityUtils securityUtils;

    @Override
    @Transactional
    @Fetch(org.hibernate.annotations.FetchMode.JOIN)
    public HttpResponse CreateRoom(CreateRoomRequest dto) {

        UserEntity currentUser = securityUtils.getCurrentUser();

        ArrayList<Long> memberIds = dto.getMemberIds();

        List<UserEntity> memberList = userRepository.findAllById(memberIds);

        Set<UserEntity> memberSet = new HashSet<>(memberList);

        RoomEntity newRoom = RoomEntity.builder()
                .description(dto.getDesc())
                .name(dto.getName())
                .users(memberSet)
                .owner(currentUser)
                .build();

        RoomEntity createRes = roomRepository.save(newRoom);

        System.out.println(createRes);

        HttpResponse response = HttpResponse.builder()
                .message("Room created!")
                .status(HttpStatus.CREATED)
                .data(createRes)
                .build();
        System.out.println(response);
        return response;
    }

    @Override
    public HttpResponse JoinRoom(JoinRoomRequest dto) {

        ArrayList<Long> UserIds = dto.getUserIds();
        Long romID = dto.getRoomId();

        List<UserEntity> UserList = userRepository.findAllById(UserIds);
        System.out.println(UserList);
        System.out.println(dto);

        RoomEntity room = null;

        System.out.println(room);

        if (room != null) {
           room.getUsers().addAll(UserList);

             roomRepository.save(room);

            return HttpResponse.builder()
                    .message("Add Users")
                    .status(HttpStatus.OK)
                    .data(dto)
                    .build();
        } else {
            return HttpResponse.builder()
                    .message("NO")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .data(dto)
                    .build();
        }

    }
}
