package com.rookies5.myspringbootlab.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

public class BookDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookCreateRequest {
        @NotBlank(message = "도서 제목은 필수 입력 값입니다.")
        private String title;

        @NotBlank(message = "저자 이름은 필수 입력 값입니다.")
        private String author;

        @NotBlank(message = "ISBN은 필수 입력 값입니다.")
        private String isbn;

        @NotNull(message = "가격은 필수 입력 값입니다.")
        @Positive(message = "가격은 0원보다 커야 합니다.")
        private Integer price;

        @PastOrPresent(message = "출판일은 미래의 날짜일 수 없습니다.")
        private LocalDate publishDate;
    }

    @Getter
    @NoArgsConstructor
    public static class BookUpdateRequest {
        @Size(min = 1, message = "제목을 수정하려면 1자 이상 입력해야 합니다.")
        private String title;

        @Size(min = 1, message = "저자 이름을 수정하려면 1자 이상 입력해야 합니다.")
        private String author;

        @Positive(message = "가격은 0원보다 커야 합니다.")
        private Integer price;

        @PastOrPresent(message = "출판일은 미래의 날짜일 수 없습니다.")
        private LocalDate publishDate;
    }

    @Getter
    @Builder
    public static class BookResponse {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;
    }
}