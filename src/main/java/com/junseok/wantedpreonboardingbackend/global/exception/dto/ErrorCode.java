package com.junseok.wantedpreonboardingbackend.global.exception.dto;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 공통 : 10,000
    INVALID_INPUT_VALUE(10000, "Invalid input value"),
    UNAUTHORIZED(10001, "Unauthorized access"),
    METHOD_NOT_ALLOWED(10002, "Method not allowed"),
    ENTITY_NOT_FOUND(10003, "Entity not found"),
    REQUEST_REJECTED(10004, "Invalid request url"),
    NUMBER_FORMAT_EXCEPTION(10005, "Number format exception"),
    REQUEST_FAILED(10006, "Request failed"),
    RESPONSE_STATUS_CODE_IS_NOT_200(10007, "Response status code is not 200"),
    CONTENT_TYPE_NOT_ALLOWED(10008, "Content-type not allowed"),

    // 검증 : 20,000
    INVALID_SIGNUP_FORMAT(20000, "Invalid sign up format, check email, password format."),
    INVALID_ALGORITHM(20001, "Invalid algorithm. See the \"Java Security Standard Algorithm Names Specification\""),

    // 비즈니스 로직 : 30,000
    NOT_FOUND_USER(30000, "Not found user."),
    NOT_FOUND_POST(30001, "Not found post."),

    // 인증 : 40,000
    UNAUTHORIZED_USER(40000, "Mismatch user password."),
    INVALID_TOKEN(40001, "Invalid token"),
    ;
    //-----------------------------------------------------------------------------------------------------------

    private final int code;
    private final String message;

    ErrorCode(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorCode valueOfOrNull(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return null;
        }
    }
}
