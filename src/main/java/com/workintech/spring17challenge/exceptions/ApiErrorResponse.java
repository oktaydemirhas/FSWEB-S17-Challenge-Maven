package com.workintech.spring17challenge.exceptions;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public class ApiErrorResponse {
    private String message;
    private Integer status;
    private Long timestamp;

    public ApiErrorResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status.value();
        this.timestamp = System.currentTimeMillis();
    }

    // Testlerin kullandığı constructor
    public ApiErrorResponse(Integer status, String message, Long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
} 