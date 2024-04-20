package com.siwon.project.global.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
    PASSWORD_MISMATCH_EXCEPTION(401, "비밀번호가 일치하지 않습니다.");
    private final int statusCode;

    private final String message;

    ErrorCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}