package com.resource.api.models;


import com.resource.api.repositories.RoomRepository;
import com.resource.api.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoomRepository roomRepository;

    @PostConstruct
    public void Seeding() {
        try {
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

            Set<UserEntity> members1 = Set.of(users.get(0), users.get(1));
            Set<UserEntity> members2 = Set.of(users.get(1), users.get(2));

            rooms.add(RoomEntity.builder()
                    .name("Room 1")
                    .description("room1")
                    .owner(users.get(0))
                    .users(members1)
                    .build());

            rooms.add(RoomEntity.builder()
                    .name("Room 2")
                    .users(members2)
                    .description("Room 2")
                    .owner(users.get(1))
                    .build());

            System.out.println(users);
            System.out.println(rooms);
            userRepository.saveAll(users);
            roomRepository.saveAll(rooms);
            log.info("Seeding user success!");
        } catch (Exception e) {
            log.error("Seeding error: " + e.getMessage());
        }
    }
}
