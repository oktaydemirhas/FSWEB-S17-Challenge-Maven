package com.workintech.spring17challenge.exceptions;

public class InvalidCourseCreditException extends RuntimeException {
    public InvalidCourseCreditException(String message) {
        super(message);
    }
} 