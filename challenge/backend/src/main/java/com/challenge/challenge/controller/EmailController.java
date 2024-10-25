package com.challenge.challenge.controller;

import com.challenge.challenge.service.EmailService;
import com.challenge.challenge.model.ContactForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.challenge.challenge.exception.BadRequestException;
import com.challenge.challenge.exception.UnauthorizedErrorException;
import com.challenge.challenge.exception.InternalServerErrorException;

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
    public ResponseEntity<?> sendEmail(@RequestBody Map<String, Object> payload) {
        try {
            ContactForm form = new ContactForm();
            form.setName((String) payload.get("name"));
            form.setMail((String) payload.get("mail"));
            form.setComment((String) payload.get("comment"));
            String recaptchaToken = (String) payload.get("recaptchaToken");

            emailService.sendEmail(form, recaptchaToken);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (BadRequestException e) {
            return createErrorResponse(HttpStatus.BAD_REQUEST, "BadRequestError", e.getMessage(), "/api/contact");
        } catch (UnauthorizedErrorException e) {
            return createErrorResponse(HttpStatus.UNAUTHORIZED, "UnauthorizedError", e.getMessage(), "/api/contact");
        } catch (InternalServerErrorException e) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "InternalServerError", e.getMessage(), "/api/contact");
        }
    }

    private ResponseEntity<Map<String, Object>> createErrorResponse(HttpStatus status, String title, String detail, String instance) {
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("type", "about:blank");
        errorAttributes.put("title", title);
        errorAttributes.put("detail", detail);
        errorAttributes.put("instance", instance);
        return new ResponseEntity<>(errorAttributes, status);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException ex) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "BadRequestError", ex.getMessage(), "/api/contact");
    }

    @ExceptionHandler(UnauthorizedErrorException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedError(UnauthorizedErrorException ex) {
        return createErrorResponse(HttpStatus.UNAUTHORIZED, "UnauthorizedError", ex.getMessage(), "/api/contact");
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<Map<String, Object>> handleInternalServerError(InternalServerErrorException ex) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "InternalServerError", ex.getMessage(), "/api/contact");
    }
}
