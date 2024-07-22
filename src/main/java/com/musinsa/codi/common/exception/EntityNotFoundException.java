package com.musinsa.codi.common.exception;

import com.musinsa.codi.common.enums.Errors;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private final Errors errors = Errors.ENTITY_NOT_FOUND;
    public EntityNotFoundException(String message) {
        super(message);
    }
}
