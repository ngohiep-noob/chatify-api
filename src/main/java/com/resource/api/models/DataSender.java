package com.resource.api.models;


import com.resource.api.repositories.RoomRepository;
import com.resource.api.repositories.UserRepository;
import com.resource.api.services.RoomService;
import com.resource.api.services.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSender {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void Seeding() {
        ArrayList<UserEntity> users = new ArrayList<>();
        users.add(UserEntity.builder()
                .username("user1")
                .password(passwordEncoder.encode("123456"))
                .email("user1@gm.com")
                .fullName("user1")
                .build());

        users.add(UserEntity.builder()
                .username("user2")
                .password(passwordEncoder.encode("123456"))
                .email("user2@gm.com")
                .fullName("user2")
                .build());

        users.add(UserEntity.builder()
                .username("user3")
                .password(passwordEncoder.encode("123456"))
                .email("user3@gm.com")
                .fullName("user3")
                .build());

        users.add(UserEntity.builder()
                .username("user4")
                .password(passwordEncoder.encode("123456"))
                .email("user4@gm.com")
                .fullName("user4")
                .build());

        ArrayList<RoomEntity> rooms = new ArrayList<>();

        rooms.add(RoomEntity.builder()
                .name("room1")
                .build());

        try {
            userRepository.saveAll(users);
            log.info("Seeding user success!");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
