package com.task.app.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse{
    private String message;
    private String timestamp;

    public ErrorResponse(String message) {
        this.status = status;
        this.message = message;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }
}
