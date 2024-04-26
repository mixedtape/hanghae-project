package com.siwon.project.global.exception.user;


import com.siwon.project.global.exception.common.CustomException;
import com.siwon.project.global.exception.common.ErrorCode;


public class AlreadyExistEmailException extends CustomException {

    public AlreadyExistEmailException() {
        super(ErrorCode.ALREADY_EXIST_EMAIL_EXCEPTION);
    }
}
