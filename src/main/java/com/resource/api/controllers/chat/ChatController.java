
package com.resource.api.controllers.chat;

import com.resource.api.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message) {
        message.setMessage("Hello user!");
        message.setSender("Server");
        return message;
    }

    @MessageMapping("/group-chat")
    public Message groupChat(@Payload Message message) {
        System.out.println(message);
        simpMessagingTemplate.convertAndSend("/chatroom/" + message.getReceiver(), message);
        return message;
    }

    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiver(), "/private", message);
        System.out.println(message.toString());
        return message;
    }
}
