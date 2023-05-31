package com.resource.api.models;

import com.resource.api.App;
import com.resource.api.config.ApplicationContextProvider;
import com.resource.api.controllers.chat.dtos.ChatDTO;
import com.resource.api.repositories.UserRepository;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "chats")
@Table(name = "chats")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public ChatDTO toDTO() {
        UserRepository userRepository = ApplicationContextProvider
                .getContext()
                .getBean(UserRepository.class);

        UserEntity user = userRepository.findById(userId).orElse(null);

        return ChatDTO.builder()
                .createdAt(createdAt)
                .message(message)
                .user(user != null ? user.toDTO() : null)
                .build();
    }
}
