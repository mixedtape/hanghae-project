package com.siwon.project.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserProfileRequestDTO {

    private String phoneNum;

    private String address;
}
