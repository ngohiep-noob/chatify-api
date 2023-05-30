package com.resource.api.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "chats")
@Table(name = "chats")
@Data
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

    @Column(nullable = false)
    private Long roomId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
