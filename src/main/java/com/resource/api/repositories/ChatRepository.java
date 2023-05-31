package com.resource.api.repositories;

import com.resource.api.models.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    public List<ChatEntity> getChatEntitiesByRoomId(Long roomId);

    public List<ChatEntity> getChatEntitiesByUserId(Long userId);

    @Query(value = "SELECT * FROM chats WHERE room_id = ?1 ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    public ChatEntity getLastMessageOfRoom(Long roomId);
}
