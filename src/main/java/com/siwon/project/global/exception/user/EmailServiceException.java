package com.siwon.project.global.exception.user;

import com.siwon.project.global.exception.common.CustomException;
import com.siwon.project.global.exception.common.ErrorCode;

public class EmailServiceException extends CustomException {
    public EmailServiceException() {
        super(ErrorCode.EMAIL_SERVICE_EXCEPTION);
    }
}
