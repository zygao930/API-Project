package com.project.project.response;

import lombok.*;

/**
 * A generic API response wrapper class for consistent structure in API responses.
 *
 * @param <T> the type of data to be returned in the response.
 */
@Getter
@Setter
@ToString
@Builder
public class ApiResponse<T> {
    private T data;
    private int code;
    private String message;

    /**
     * Creates a success response.
     *
     * @param data the response data.
     * @param code the HTTP status or application-specific code.
     * @param <T>  the type of data.
     * @return an ApiResponse representing success.
     */
    public static <T> ApiResponse<T> success(T data, int code) {
        return ApiResponse.<T>builder().data(data)
                .code(code)
                .message("Success")
                .build();
    }

    /**
     * Creates a failure response.
     *
     * @param data    the response data.
     * @param code    the HTTP status or application-specific code.
     * @param message the error message.
     * @param <T>     the type of data.
     * @return an ApiResponse representing failure.
     */
    public static <T> ApiResponse<T> fail(T data, int code, String message) {
        return ApiResponse.<T>builder().data(data)
                .code(code)
                .message(message)
                .build();
    }
}
