package com.musinsa.codi.common.exception;

import com.musinsa.codi.common.enums.Errors;
import lombok.Getter;

@Getter
public class DuplicationDataException extends RuntimeException {
    private final Errors errors = Errors.DUPLICATION_DATA;

    public DuplicationDataException(String message) {
        super(message);
    }
}
