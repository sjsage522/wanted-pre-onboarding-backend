package com.junseok.wantedpreonboardingbackend.global.exception.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private Error error;

    public ErrorResponse(ErrorCode errorCode) {
        this.error = new Error(errorCode);
    }

    public ErrorResponse(int code, String message) {
        this.error = new Error(code, message);
    }

    @Getter
    @AllArgsConstructor
    public static class Error {
        private int code;
        private String message;

        public Error(ErrorCode errorCode) {
            this.code = errorCode.getCode();
            this.message = errorCode.getMessage();
        }
    }
}
