package com.siwon.project.global.exception.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
    //jwt
    INVALID_JWT_SIGNATURE_EXCEPTION(401, "유효하지 않는 JWT 서명 입니다."),
    EXPIRED_JWT_TOKEN_EXCEPTION(401, "만료된 JWT token 입니다."),
    UNSUPPORTED_JWT_TOKEN_EXCEPTION(401, "지원되지 않는 JWT 토큰 입니다."),
    INVALID_JWT_TOKEN_EXCEPTION(401, "잘못된 JWT 토큰 입니다."),
    REFRESH_TOKEN_NOT_FOUND_EXCEPTION(404, "Refresh Token을 찾을 수 없습니다."),
    EXPIRED_REFRESH_TOKEN_EXCEPTION(401, "만료된 Refresh Token 입니다."),
    REVOKED_REFRESH_TOKEN_EXCEPTION(401, "사용이 중지된 Refresh Token 입니다."),
    //user
    NOT_FOUND_USER_EXCEPTION(400, "해당 유저가 존재하지 않습니다"),
    PASSWORD_CONFIRMATION_EXCEPTION(401, "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    PASSWORD_MISMATCH_EXCEPTION(401, "비밀번호가 일치하지 않습니다.");
    private final int statusCode;

    private final String message;

    ErrorCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}