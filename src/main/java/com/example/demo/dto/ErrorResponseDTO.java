package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponseDTO {
    private LocalDateTime timestamp;
    private String message;
    private List<String> details;
    private String path;

    public ErrorResponseDTO(String message, List<String> details, String path) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.details = details;
        this.path = path;
    }

    // Getters and Setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getDetails() {
        return details;
    }

    public String getPath() {
        return path;
    }
}