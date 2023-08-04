package com.junseok.wantedpreonboardingbackend.global.exception.dto;

import lombok.Getter;

@Getter
public enum ErrorCode {

    /**
     * ---- 서버 에러 분류 (10,000 ~ ) ----
     * common        : 10,000
     * 인증           : 20,000
     *
     * ---- 서비스 단위 분류 (20,000 ~ ) ----
     *
     */

    //-----------------------------------------서버 에러 분류 (10,000 ~ )-----------------------------------------
    // common : 10,000
    INVALID_INPUT_VALUE(10000, "Invalid input value"),
    UNAUTHORIZED(10001, "Unauthorized access"),
    METHOD_NOT_ALLOWED(10002, "Method not allowed"),
    ENTITY_NOT_FOUND(10003, "Entity not found"),
    REQUEST_REJECTED(10004, "Invalid request url"),
    NUMBER_FORMAT_EXCEPTION(10005, "Number format exception"),
    REQUEST_FAILED(10006, "Request failed"),
    RESPONSE_STATUS_CODE_IS_NOT_200(10007, "Response status code is not 200"),
    CONTENT_TYPE_NOT_ALLOWED(10008, "Content-type not allowed"),
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
