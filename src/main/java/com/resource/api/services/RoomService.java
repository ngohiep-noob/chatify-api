package com.resource.api.services;

import com.resource.api.controllers.HttpResponse;
import com.resource.api.controllers.room.dtos.CreateRoomRequest;
import com.resource.api.controllers.room.dtos.JoinRoomRequest;
import com.resource.api.controllers.room.dtos.RoomDTO;
import com.resource.api.models.RoomEntity;
import com.resource.api.models.UserEntity;
import com.resource.api.repositories.RoomRepository;
import com.resource.api.repositories.UserRepository;
import com.resource.api.services.intefaces.IRoomService;
import com.resource.api.utils.SecurityUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.hibernate.annotations.Fetch;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        ArrayList<String> memberNames = dto.getMemberNames();
        List<UserEntity> memberList = userRepository.findAllByUsernameIn(memberNames);

        memberList.add(currentUser);
        System.out.println(memberList);
        Set<UserEntity> memberSet = new HashSet<>(memberList);

        RoomEntity newRoom = RoomEntity.builder()
                .description(dto.getDesc())
                .name(dto.getName())
                .users(memberSet)
                .ownerId(currentUser.getId())
                .build();

        RoomEntity res = roomRepository.save(newRoom);

        return HttpResponse.builder()
                .message("Room created!")
                .status(HttpStatus.CREATED)
                .data(res.toDTO())
                .build();
    }

    @Override
    public HttpResponse JoinRoom(JoinRoomRequest dto) {
        UserEntity currentUser = securityUtils.getCurrentUser();

        RoomEntity room = roomRepository.findById(dto.getRoomId()).orElse(null);

        if (room == null)
            return HttpResponse.builder()
                    .message("Room not found!")
                    .status(HttpStatus.NOT_FOUND)
                    .data(null)
                    .build();

        if (!Objects.equals(room.getOwnerId(), currentUser.getId()))
            return HttpResponse.builder()
                    .message("You are not owner of this room!")
                    .status(HttpStatus.FORBIDDEN)
                    .data(null)
                    .build();

        ArrayList<String> usernames = dto.getUsernames();

        List<UserEntity> userList = userRepository.findAllByUsernameIn(usernames);

        room.getUsers().addAll(userList);

        roomRepository.save(room);

        return HttpResponse.builder()
                .message("Add users to room successfully!")
                .status(HttpStatus.OK)
                .data(room.toDTO())
                .build();
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public HttpResponse GetChatHistory(Long roomId) {
        RoomEntity room = roomRepository.findById(roomId).orElse(null);

        if (room != null) {
            Integer countMembers = room.getUsers().size();
            RoomDTO roomDTO = room.toDTO();
            roomDTO.setMemberCount(countMembers);
            return HttpResponse.builder()
                    .message("Get Chat History")
                    .status(HttpStatus.OK)
                    .data(roomDTO)
                    .build();
        }

        return HttpResponse.builder()
                .message("Room not found")
                .status(HttpStatus.NOT_FOUND)
                .build();
    }
}
