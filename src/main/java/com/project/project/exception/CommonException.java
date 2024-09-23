package com.project.project.exception;

import lombok.*;

/**
 * A custom exception class that captures an error code and message
 * for more detailed error handling.
 */
@Getter
@ToString
public class CommonException extends Exception {
    private final int code;
    private final String message;

    /**
     * Constructor to create a CommonException with a specific error code and message.
     *
     * @param code    the application-specific error code.
     * @param message the error message.
     */
    public CommonException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return 0;
    }
}
