
package com.resource.api.controllers.chat;

import com.resource.api.models.Message;
import com.resource.api.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private SimpMessagingTemplate simpMessagingTemplate;

    private final ChatService chatService;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message) {
        message.setMessage("Hello user!");
        return message;
    }

    @MessageMapping("/group-chat")
    public Message groupChat(@Payload Message message) {
        System.out.println(message);

        simpMessagingTemplate.convertAndSend("/chatroom/" + message.getReceiver(), message);

        chatService.SaveMessage(message);

        return message;
    }

    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiver(), "/private", message);
        System.out.println(message.toString());
        return message;
    }
}
