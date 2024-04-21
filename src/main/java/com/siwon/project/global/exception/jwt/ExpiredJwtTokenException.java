package com.siwon.project.global.exception.jwt;

import com.siwon.project.global.exception.common.CustomException;
import com.siwon.project.global.exception.common.ErrorCode;

public class ExpiredJwtTokenException extends CustomException {

    public ExpiredJwtTokenException(Throwable cause) {
        super(ErrorCode.EXPIRED_JWT_TOKEN_EXCEPTION, cause);
    }
}
