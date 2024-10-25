package com.challenge.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedErrorException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedError(UnauthorizedErrorException ex) {
        return createErrorResponse(HttpStatus.UNAUTHORIZED, "UnauthorizedError", ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException ex) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "BadRequestError", ex.getMessage());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<Map<String, Object>> handleInternalServerError(InternalServerErrorException ex) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "InternalServerError", ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> createErrorResponse(HttpStatus status, String title, String detail) {
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("type", "about:blank");
        errorAttributes.put("title", title);
        errorAttributes.put("detail", detail);
        errorAttributes.put("instance", "/api-endpoint");
        return new ResponseEntity<>(errorAttributes, status);
    }
}
