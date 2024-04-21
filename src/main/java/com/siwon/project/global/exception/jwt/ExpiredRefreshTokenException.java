package com.siwon.project.global.exception.jwt;


import com.siwon.project.global.exception.common.CustomException;
import com.siwon.project.global.exception.common.ErrorCode;

public class ExpiredRefreshTokenException extends CustomException {

    public ExpiredRefreshTokenException() {
        super(ErrorCode.EXPIRED_REFRESH_TOKEN_EXCEPTION);
    }
}
