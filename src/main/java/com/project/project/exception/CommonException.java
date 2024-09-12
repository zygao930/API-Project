package com.project.project.exception;
import lombok.*;

@Getter
@ToString

public class CommonException extends Exception {
    private final int code;
    private final String message;

    public CommonException(int code, String message){
        super(message);
        this.code = code;
        this.message = message;
    }
}
