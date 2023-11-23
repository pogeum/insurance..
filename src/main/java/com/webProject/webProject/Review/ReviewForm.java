package com.webProject.webProject.Review;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
class ReviewForm {
    @NotEmpty(message = "내용을 입력하세요.")
    private String content;

    @NotNull(message = "평점을 입력하세요.")
    private Double rating; // double 대신 Double 사용

    private List<Integer> tagList; // 컬렉션의 빈 여부만을 검증하는 @NotEmpty
}
