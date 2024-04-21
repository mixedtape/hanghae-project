package com.siwon.project.global.exception.jwt;

import com.siwon.project.global.exception.common.CustomException;
import com.siwon.project.global.exception.common.ErrorCode;

public class RefreshTokenNotFoundException extends CustomException {

    public RefreshTokenNotFoundException() {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND_EXCEPTION);
    }
}
