package com.junseok.wantedpreonboardingbackend.global.exception;

import com.junseok.wantedpreonboardingbackend.global.exception.dto.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 엔티티 조회 시 Exception
 */
public class EntityNotFoundException extends CustomException {

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode, HttpStatus.NOT_FOUND);
    }
}
