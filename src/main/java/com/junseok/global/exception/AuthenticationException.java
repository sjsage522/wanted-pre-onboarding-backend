package com.junseok.global.exception;

import com.junseok.global.exception.dto.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 인증 Exception
 */
public class AuthenticationException extends CustomException {

	public AuthenticationException(ErrorCode errorCode) {
		super(errorCode, HttpStatus.UNAUTHORIZED);
	}
}
