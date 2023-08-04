package com.junseok.global.exception;

import com.junseok.global.exception.dto.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 엔티티 조회 시 Exception
 */
public class EntityNotFoundException extends CustomException {

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode, HttpStatus.NOT_FOUND);
    }
}
