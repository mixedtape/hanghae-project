package com.siwon.project.global.exception.user;


import com.siwon.project.global.exception.common.CustomException;
import com.siwon.project.global.exception.common.ErrorCode;

public class PasswordConfirmationException extends CustomException {

    public PasswordConfirmationException() {
        super(ErrorCode.PASSWORD_CONFIRMATION_EXCEPTION);
    }
}
