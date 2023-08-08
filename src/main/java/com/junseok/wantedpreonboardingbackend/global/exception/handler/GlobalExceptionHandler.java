package com.junseok.wantedpreonboardingbackend.global.exception.handler;

import com.junseok.wantedpreonboardingbackend.global.dto.ApiResult;
import com.junseok.wantedpreonboardingbackend.global.exception.CustomException;
import com.junseok.wantedpreonboardingbackend.global.exception.dto.ErrorCode;
import com.junseok.wantedpreonboardingbackend.global.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static com.junseok.wantedpreonboardingbackend.global.dto.ApiResult.failed;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ApiResult<ErrorResponse>> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        String message = e.getMessage();
        HttpStatus status = e.getStatus();
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), message);
        return new ResponseEntity<>(failed(errorResponse), status);
    }

    // 지원하지 않는 method 405 error
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ApiResult<ErrorResponse>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(failed(errorResponse), HttpStatus.METHOD_NOT_ALLOWED);
    }

    // @RequestBody validation error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResult<ErrorResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponse errorResponse = getErrorResponseByMessage(message);

        return new ResponseEntity<>(failed(errorResponse), HttpStatus.BAD_REQUEST);
    }

    // TypeMismatchException
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResult<ErrorResponse>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String message = e.getMessage();
        ErrorResponse errorResponse = getErrorResponseByMessage(message);

        return new ResponseEntity<>(failed(errorResponse), HttpStatus.BAD_REQUEST);
    }

    // @ModelAttribute validation error
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResult<ErrorResponse>> handleBindException(BindException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponse errorResponse = getErrorResponseByMessage(message);

        return new ResponseEntity<>(failed(errorResponse), HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse getErrorResponseByMessage(String message) {
        ErrorCode errorCode = ErrorCode.valueOfOrNull(message);

        if (errorCode == null) {
            return new ErrorResponse(ErrorCode.INVALID_INPUT_VALUE.getCode(), message);
        } else {
            return new ErrorResponse(errorCode);
        }
    }
}
