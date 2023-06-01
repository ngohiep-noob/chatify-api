
package com.resource.api.controllers.chat;

import com.resource.api.models.Message;
import com.resource.api.models.UserEntity;
import com.resource.api.repositories.UserRepository;
import com.resource.api.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChatController {
    private final UserRepository userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

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

        UserEntity sender = userRepository.findById(Long.valueOf(message.getSenderId())).orElse(null);
        assert sender != null;
        message.setSenderName(sender.getUsername());

        simpMessagingTemplate.convertAndSend("/chatroom/" + message.getReceiverId(), message);

        chatService.SaveMessage(message);

        return message;
    }

    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverId(), "/private", message);
        System.out.println(message.toString());
        return message;
    }

}
