package com.resource.api.models;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Message {
    private String senderId; // user id
    private String receiverId; // room id
    private String senderName;
    private String message;
    private Date date;
}
