package com.resource.api.services;

import com.resource.api.controllers.HttpResponse;
import com.resource.api.controllers.room.dtos.CreateRoomRequest;
import com.resource.api.controllers.room.dtos.JoinRoomRequest;
import com.resource.api.controllers.room.dtos.RoomListItem;
import com.resource.api.models.RoomEntity;
import com.resource.api.models.UserEntity;
import com.resource.api.repositories.RoomRepository;
import com.resource.api.repositories.UserRepository;
import com.resource.api.services.intefaces.IRoomService;
import com.resource.api.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {

    private final RoomRepository roomRepository;

    private final UserRepository userRepository;

    private final SecurityUtils securityUtils;

    @Override
    @Transactional
    public HttpResponse CreateRoom(CreateRoomRequest dto) {
        UserEntity currentUser = securityUtils.getCurrentUser();

        ArrayList<Long> memberIds = dto.getMembersId();

        List<UserEntity> memberList = userRepository.findAllById(memberIds);

        Set<UserEntity> memberSet = new HashSet<>(memberList);

        RoomEntity newRoom = RoomEntity.builder()
                .description(dto.getDesc())
                .name(dto.getName())
                .users(memberSet)
                .owner(currentUser)
                .build();

        roomRepository.save(newRoom);

        return HttpResponse.builder()
                .message("Room created!")
                .status(HttpStatus.CREATED)
                .data(newRoom)
                .build();
    }

    @Override
    public HttpResponse JoinRoom(JoinRoomRequest dto) {
        return null;
    }
}
