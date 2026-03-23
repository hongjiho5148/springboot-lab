package com.rookies5.myspringbootlab.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    RESOURCE_NOT_FOUND("%s을(를) 찾을 수 없습니다. (%s: %s)", HttpStatus.NOT_FOUND),
    ISBN_DUPLICATE("이미 존재하는 ISBN입니다: %s", HttpStatus.CONFLICT);

    private final String messageTemplate;
    private final HttpStatus httpStatus;

    public String formatMessage(Object... args) {
        return String.format(messageTemplate, args);
    }
}