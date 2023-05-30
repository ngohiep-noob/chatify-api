package com.resource.api.models;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Message {
    private String sender;
    private String receiver;
    private String message;
    private Date date;
}
