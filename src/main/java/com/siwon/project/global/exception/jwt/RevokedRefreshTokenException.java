package com.siwon.project.global.exception.jwt;

import com.siwon.project.global.exception.common.CustomException;
import com.siwon.project.global.exception.common.ErrorCode;

public class RevokedRefreshTokenException extends CustomException {

    public RevokedRefreshTokenException() {
        super(ErrorCode.REVOKED_REFRESH_TOKEN_EXCEPTION);
    }
}
