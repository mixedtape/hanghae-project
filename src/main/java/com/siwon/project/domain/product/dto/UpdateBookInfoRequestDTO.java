package com.siwon.project.domain.product.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UpdateBookInfoRequestDTO {
    @NotEmpty(message = "책 제목을 입력해 주세요")
    private String Name;
    @NotEmpty(message = "가격을 입력해 주세요")
    private Long Price;
    @NotEmpty(message = "출판사를 입력해 주세요")
    private String publisher;
    @NotEmpty(message = "저자를 입력해 주세요")
    private String author;
    private String description;
    @NotEmpty(message = "수량을 입력해 주세요")
    private Long stock;
}
