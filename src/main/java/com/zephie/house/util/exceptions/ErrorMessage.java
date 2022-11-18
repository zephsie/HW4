package com.zephie.house.util.exceptions;

import java.time.LocalDateTime;

public class ErrorMessage {
    private String message;

    private LocalDateTime timestamp;

    public ErrorMessage(String message, LocalDateTime timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}