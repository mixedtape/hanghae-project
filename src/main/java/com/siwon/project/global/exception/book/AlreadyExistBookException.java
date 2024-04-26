package com.siwon.project.global.exception.book;


import com.siwon.project.global.exception.common.CustomException;
import com.siwon.project.global.exception.common.ErrorCode;

public class AlreadyExistBookException extends CustomException {

    public AlreadyExistBookException() {
        super(ErrorCode.ALREADY_EXIST_BOOK_EXCEPTION);
    }

}
