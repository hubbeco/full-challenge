package com.challenge.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedErrorException extends RuntimeException {
    private final String type;
    private final String title;
    private final String detail;
    private final String instance;

    public UnauthorizedErrorException(String detail, String instance) {
        super(detail);
        this.type = "about:blank";
        this.title = "UnauthorizedError";
        this.detail = detail;
        this.instance = instance;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public String getInstance() {
        return instance;
    }
}
