package com.example.taskservice.handlers;

import java.time.LocalDateTime;

public class ValidationErrorResponse {

    private String errorMessages;
    private LocalDateTime timestamp;

    public ValidationErrorResponse(String errorMessages) {
        this.errorMessages = errorMessages;
        this.timestamp = LocalDateTime.now();
    }

}