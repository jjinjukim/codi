package com.musinsa.codi.common.exception;

import com.musinsa.codi.common.enums.Errors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class ErrorResponse {
    private final String code;
    private final String message;

    public static ErrorResponse of(Errors errors) {
        return ErrorResponse.builder()
                .code(errors.getCode())
                .message(errors.getMessage())
                .build();
    }

    public static ErrorResponse of(String code, String message) {
        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .build();
    }
}
