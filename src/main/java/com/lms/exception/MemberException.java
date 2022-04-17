package com.lms.exception;

import org.springframework.http.HttpStatus;

public class MemberException extends RuntimeException{

    private static final long serialVersionUID = 2457189159617736175L;

    private final HttpStatus status;
    private String message;


    /**
     * Arg constructor.
     * @param message
     * @param status
     */
    public MemberException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
