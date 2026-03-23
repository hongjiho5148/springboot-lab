package com.rookies5.myspringbootlab.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final HttpStatus httpStatus;

    // 1. 기존 생성자 (메시지만 입력할 때)
    public BusinessException(String message) {
        this(message, HttpStatus.EXPECTATION_FAILED);
    }

    // 2. 기존 생성자 (메시지와 상태 코드를 직접 입력할 때)
    public BusinessException(String message, HttpStatus httpStatus) {
        super(message); // 부모 클래스인 RuntimeException에게 메시지를 전달
        this.httpStatus = httpStatus;
    }


    // ErrorCode 전용 생성자
    public BusinessException(ErrorCode errorCode, Object... args) {
        super(errorCode.formatMessage(args)); // ErrorCode에 정의된 메시지 포맷을 완성해서 부모에게 전달
        this.httpStatus = errorCode.getHttpStatus(); // ErrorCode에 정의된 상태 코드를 꺼내옴
    }
}