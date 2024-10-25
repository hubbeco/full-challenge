package com.challenge.challenge.controller;

import com.challenge.challenge.service.EmailService;
import com.challenge.challenge.model.ContactForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/contact")
    public ResponseEntity<?> sendEmail(@RequestBody ContactForm form) {
        try {
            emailService.sendEmail(form);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse("BadRequestError", e.getMessage(), "/api/contact"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorResponse("InternalServerError", "Some generic error name.", "/api/contact"));
        }
    }

    private Map<String, String> createErrorResponse(String title, String detail, String instance) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("type", "about:blank");
        errorResponse.put("title", title);
        errorResponse.put("detail", detail);
        errorResponse.put("instance", instance);
        return errorResponse;
    }
}
