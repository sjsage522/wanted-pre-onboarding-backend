package com.junseok.wantedpreonboardingbackend.global.exception.controller;

import com.junseok.wantedpreonboardingbackend.global.dto.ApiResult;
import com.junseok.wantedpreonboardingbackend.global.exception.dto.ErrorCode;
import com.junseok.wantedpreonboardingbackend.global.exception.dto.ErrorResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {
    @GetMapping("/error")
    public ResponseEntity<ApiResult<?>> handleError() {
        return new ResponseEntity<>(
                ApiResult.failed(new ErrorResponse(ErrorCode.REQUEST_FAILED)),
                HttpStatus.NOT_FOUND
        );
    }
}
