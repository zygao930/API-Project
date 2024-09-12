package com.project.project.response;
import lombok.*;

@Getter
@Setter
@ToString
@Builder

public class ApiResponse<T> {
    private T data;
    private int code;
    private String message;

    public static <T> ApiResponse<T> success(T data, int code){
        return ApiResponse.<T>builder().data(data)
                .code(code)
                .message("Success")
                .build();
    }

    public static <T> ApiResponse<T> fail(T data, int code, String message){
        return ApiResponse.<T>builder().data(data)
                .code(code)
                .message(message)
                .build();
    }
}
