package com.resource.api.models;


import com.resource.api.repositories.ChatRepository;
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
    private final ChatRepository chatRepository;

    private ChatEntity newChat(RoomEntity room, UserEntity user, String message) {
        return ChatEntity.builder()
                .message(message)
                .userId(user.getId())
                .room(room)
                .build();
    }

    @PostConstruct
    public void Seeding() {
        try {
            ArrayList<UserEntity> users = new ArrayList<>();
            users.add(UserEntity.builder()
                    .username("ngohiep")
                    .password(passwordEncoder.encode("123456"))
                    .email("ngohiep@gm.com")
                    .fullName("Ngo Duc Hoang Hiep")
                    .build());

            users.add(UserEntity.builder()
                    .username("chanhnghia")
                    .password(passwordEncoder.encode("123456"))
                    .email("chanhnghia@gm.com")
                    .fullName("Nguyen Chanh Nghia")
                    .build());

            users.add(UserEntity.builder()
                    .username("hoangkhanh")
                    .password(passwordEncoder.encode("123456"))
                    .email("hoangkhanh@gm.com")
                    .fullName("Huynh Hoang Khanh")
                    .build());

            users.add(UserEntity.builder()
                    .username("quochung")
                    .password(passwordEncoder.encode("123456"))
                    .email("quochung@gm.com")
                    .fullName("Nguyen Quoc Hung")
                    .build());

            ArrayList<RoomEntity> rooms = new ArrayList<>();

            Set<UserEntity> members1 = Set.of(users.get(0), users.get(1));
            Set<UserEntity> members2 = Set.of(users.get(1), users.get(2));

            rooms.add(RoomEntity.builder()
                    .name("Room 1")
                    .description("Room 1")
                    .ownerId(users.get(0).getId())
                    .users(members1)
                    .build());

            rooms.add(RoomEntity.builder()
                    .name("Room 2")
                    .users(members2)
                    .description("Room 2")
                    .ownerId(users.get(1).getId())
                    .build());

            userRepository.saveAll(users);
            System.out.println(users);

            roomRepository.saveAll(rooms);
            System.out.println(rooms);

            Set<ChatEntity> messages = Set.of(
                    newChat(rooms.get(0), users.get(0), "Hello room 1"),
                    newChat(rooms.get(0), users.get(1), "Hi room 1"),
                    newChat(rooms.get(0), users.get(2), "My name is user 3"),
                    newChat(rooms.get(1), users.get(1), "Hello room 2"),
                    newChat(rooms.get(1), users.get(2), "Hi room 2")
            );

            chatRepository.saveAll(messages);
            System.out.println(messages);
            log.info("Seeding user success!");
        } catch (Exception e) {
            log.error("Seeding error: " + e.getMessage());
        }
    }
}
