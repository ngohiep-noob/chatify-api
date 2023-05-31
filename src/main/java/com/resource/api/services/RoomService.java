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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

        return HttpResponse.builder()
                .message("Room created!")
                .status(HttpStatus.CREATED)
                .data(createRes)
                .build();
    }

    @Override
    public HttpResponse JoinRoom(JoinRoomRequest dto) {

        ArrayList<Long> UserIds = dto.getUserIds();
        Long roomID = dto.getRoomId();

        List<UserEntity> userList = userRepository.findAllById(UserIds);

        System.out.println(userList);
        System.out.println(dto);

        RoomEntity room = roomRepository.findById(roomID).orElse(null);

        System.out.println(room);
        if (room != null) {
            room.getUsers().addAll(userList);

            roomRepository.save(room);
        }

        return HttpResponse.builder()
                .message("Add Users")
                .status(HttpStatus.OK)
                .data(room)
                .build();
    }
}
