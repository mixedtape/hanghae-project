package com.siwon.project.global.exception.book;

import com.siwon.project.global.exception.common.CustomException;
import com.siwon.project.global.exception.common.ErrorCode;

public class BookNotFoundException extends CustomException {

    public BookNotFoundException() {
        super(ErrorCode.BOOK_NOT_FOUND_EXCEPTION);
    }

}
