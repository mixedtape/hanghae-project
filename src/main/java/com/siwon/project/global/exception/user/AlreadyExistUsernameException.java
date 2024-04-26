package com.siwon.project.global.exception.user;


import com.siwon.project.global.exception.common.CustomException;
import com.siwon.project.global.exception.common.ErrorCode;

public class AlreadyExistUsernameException extends CustomException {

    public AlreadyExistUsernameException() {
        super(ErrorCode.ALREADY_EXIST_USERNAME_EXCEPTION);
    }
}
