package com.rookies5.myspringbootlab.mapper;

import com.rookies5.myspringbootlab.dto.BookDTO;
import com.rookies5.myspringbootlab.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    // 1. DTO -> Entity 변환
    public Book toEntity(BookDTO.BookCreateRequest request) {
        if (request == null) return null;
        
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPrice(request.getPrice());
        book.setPublishDate(request.getPublishDate());
        return book;
    }

    // 2. Entity -> DTO 변환
    public BookDTO.BookResponse toResponse(Book book) {
        if (book == null) return null;
        
        return BookDTO.BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .publishDate(book.getPublishDate())
                .build();
    }
}