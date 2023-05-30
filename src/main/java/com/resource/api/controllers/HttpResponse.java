package com.resource.api.controllers;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponse {
    private String message;
    private Object data;
    private HttpStatus status;
}
