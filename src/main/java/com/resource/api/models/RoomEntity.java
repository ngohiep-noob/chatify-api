package com.resource.api.models;

import com.resource.api.controllers.chat.dtos.ChatDTO;
import com.resource.api.controllers.room.dtos.RoomDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Entity(name = "rooms")
@Table(name = "rooms")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"users"})
public class RoomEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_rooms",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> users = new HashSet<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ChatEntity> chats = new HashSet<>();

    private Long ownerId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public RoomDTO toDTO() {
        ArrayList<ChatDTO> chats = new ArrayList<>();

        if (this.chats != null)
            this.chats.forEach(chat -> {
                chats.add(chat.toDTO());
            });

        // sort by createdAt
        chats.sort(Comparator.comparing(ChatDTO::getCreatedAt));

        return RoomDTO.builder()
                .id(this.id)
                .name(this.name)
                .desc(this.description)
                .ownerId(this.ownerId)
                .createdAt(this.createdAt != null ? this.createdAt.toString() : null)
                .chats(chats)
                .build();
    }
}
