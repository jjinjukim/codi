package com.musinsa.codi.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum Errors {
    DUPLICATION_DATA(HttpStatus.BAD_REQUEST, "D001", "중복된 데이터가 삽입되었습니다."),
    ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "E001", "해당 엔티티를 찾을 수 없습니다."),
    BAD_REQUEST_INVALID_VALUE(HttpStatus.BAD_REQUEST, "I001", "요청 값이 잘못되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
