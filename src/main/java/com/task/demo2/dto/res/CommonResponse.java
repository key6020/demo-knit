package com.task.demo2.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CommonResponse<T> {
    private int status;
    public String message;
    private T data;

    public static <T> CommonResponse<T> response(final int status, final String message) {
        return response(status, message, null);
    }

    public static <T> CommonResponse<T> response(final int status, final String message, final T data) {
        return CommonResponse.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }
}
