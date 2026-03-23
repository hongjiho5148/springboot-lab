package com.rookies5.myspringbootlab.mapper;

import com.rookies5.myspringbootlab.dto.BookDTO;
import com.rookies5.myspringbootlab.entity.Book;
import com.rookies5.myspringbootlab.entity.BookDetail;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    // 1. DTO -> Entity 변환 (등록할 때 사용)
    public Book toEntity(BookDTO.Request request) {
        if (request == null) return null;

        // 우리가 엔티티에 추가했던 @Builder를 활용해 깔끔하게 조립합니다.
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishDate(request.getPublishDate())
                .build();

        // 1:1 상세 정보(BookDetail)가 같이 들어왔다면 매핑해 줍니다.
        if (request.getDetailRequest() != null) {
            BookDetail detail = BookDetail.builder()
                    .description(request.getDetailRequest().getDescription())
                    .language(request.getDetailRequest().getLanguage())
                    .pageCount(request.getDetailRequest().getPageCount())
                    .publisher(request.getDetailRequest().getPublisher())
                    .coverImageUrl(request.getDetailRequest().getCoverImageUrl())
                    .edition(request.getDetailRequest().getEdition())
                    .build();

            // Book.java에 만들어둔 '연관관계 편의 메서드'를 사용해 양방향 연결!
            book.setBookDetail(detail);
        }

        return book;
    }

    // 2. Entity -> DTO 변환 (조회, 응답할 때 사용)
    public BookDTO.Response toResponse(Book book) {
        if (book == null) return null;

        return BookDTO.Response.fromEntity(book);
    }
}