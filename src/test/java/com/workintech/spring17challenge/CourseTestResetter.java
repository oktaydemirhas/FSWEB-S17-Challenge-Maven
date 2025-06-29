package com.workintech.spring17challenge;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CourseTestResetter implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext context) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.delete("http://localhost:9000/workintech/courses/reset");
        } catch (Exception ignored) {}
    }
} 