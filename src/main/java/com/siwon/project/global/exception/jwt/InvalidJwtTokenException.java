package com.siwon.project.global.exception.jwt;


import com.siwon.project.global.exception.common.CustomException;
import com.siwon.project.global.exception.common.ErrorCode;

public class InvalidJwtTokenException extends CustomException {

    public InvalidJwtTokenException(Throwable cause) {
        super(ErrorCode.INVALID_JWT_TOKEN_EXCEPTION, cause);
    }
    public InvalidJwtTokenException() {
        super(ErrorCode.INVALID_JWT_TOKEN_EXCEPTION);
    }
}
