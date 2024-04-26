package com.siwon.project.global.exception.user;


import com.siwon.project.global.exception.common.CustomException;
import com.siwon.project.global.exception.common.ErrorCode;

public class AuthenticationMismatchException extends CustomException {

    public AuthenticationMismatchException() {
        super(ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION);
    }
}
