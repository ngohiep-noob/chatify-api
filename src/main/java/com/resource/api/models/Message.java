package com.resource.api.models;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Message {
    private String sender; // user id
    private String receiver; // room id
    private String message;
    private Date date;
}
