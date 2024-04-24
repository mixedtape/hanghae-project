package com.siwon.project.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangePasswordDTO {

    @Pattern(regexp = "^[a-zA-Z0-9]{4,}$",
            message = "올바른 형식의 비밀번호가 아닙니다. 문자(대문자/소문자) 혹은 숫자를 4글자 이상 작성해주세요.")
    private String password;
}
