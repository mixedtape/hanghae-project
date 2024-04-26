package com.siwon.project.global.exception.user;


import com.siwon.project.global.exception.common.CustomException;
import com.siwon.project.global.exception.common.ErrorCode;

public class InvalidEmailCodeException extends CustomException {
    public InvalidEmailCodeException() {
        super(ErrorCode.INVALID_EMAIL_CODE_EXCEPTION);
    }
}
