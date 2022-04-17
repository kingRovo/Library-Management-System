package com.lms.exception;

import org.springframework.http.HttpStatus;

public class BookItemException extends RuntimeException{

    private final HttpStatus status;
    private String message;


    /**
     * Arg constructor.
     * @param message
     * @param status
     */
    public BookItemException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
