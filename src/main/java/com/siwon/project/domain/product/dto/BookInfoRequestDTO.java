package com.siwon.project.domain.product.dto;

import jakarta.persistence.Column;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class BookInfoRequestDTO {

    @NotEmpty(message = "책 제목을 입력해 주세요")
    private String bookName;
}
