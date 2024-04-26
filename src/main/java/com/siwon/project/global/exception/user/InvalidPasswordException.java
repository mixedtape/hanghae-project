package com.siwon.project.global.exception.user;


import com.siwon.project.global.exception.common.CustomException;
import com.siwon.project.global.exception.common.ErrorCode;

public class InvalidPasswordException extends CustomException {

    public InvalidPasswordException() {
        super(ErrorCode.PASSWORD_MISMATCH_EXCEPTION);
    }
}
