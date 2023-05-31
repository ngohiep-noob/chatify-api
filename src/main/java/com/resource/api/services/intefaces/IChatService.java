package com.resource.api.services.intefaces;

import com.resource.api.models.ChatEntity;
import com.resource.api.models.Message;

import java.util.ArrayList;

public interface IChatService {
    public ChatEntity SaveMessage(Message msg);

    public ArrayList<ChatEntity> GetMessageByRoomId(Long roomId);
}
