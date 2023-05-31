package com.resource.api.services;

import com.resource.api.models.ChatEntity;
import com.resource.api.models.Message;
import com.resource.api.models.RoomEntity;
import com.resource.api.repositories.ChatRepository;
import com.resource.api.repositories.RoomRepository;
import com.resource.api.services.intefaces.IChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService implements IChatService {
    private final ChatRepository chatRepository;

    private final RoomRepository roomRepository;

    @Override
    public void SaveMessage(Message msg) {
        try {
            RoomEntity room = roomRepository
                    .findById(Long.valueOf(msg.getReceiver()))
                    .orElse(null);

            if (room == null) {
                return;
            }

            ChatEntity newMsg = ChatEntity.builder()
                    .userId(Long.valueOf(msg.getSender()))
                    .room(room)
                    .message(msg.getMessage())
                    .build();

            chatRepository.save(newMsg);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
