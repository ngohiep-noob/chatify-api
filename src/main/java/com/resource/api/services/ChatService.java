package com.resource.api.services;

import com.resource.api.models.ChatEntity;
import com.resource.api.models.Message;
import com.resource.api.repositories.ChatRepository;
import com.resource.api.services.intefaces.IChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ChatService implements IChatService {
    private final ChatRepository chatRepository;

    @Override
    public ChatEntity SaveMessage(Message msg) {
        try {
            ChatEntity newMsg = ChatEntity.builder()
                    .userId(Long.valueOf(msg.getSender()))
                    .roomId(Long.valueOf(msg.getReceiver()))
                    .message(msg.getMessage())
                    .build();
            chatRepository.save(newMsg);
            return newMsg;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public ArrayList<ChatEntity> GetMessageByRoomId(Long roomId) {
        return null;
    }
}
