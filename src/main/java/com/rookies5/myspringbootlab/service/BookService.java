package com.rookies5.myspringbootlab.service;

import com.rookies5.myspringbootlab.dto.BookDTO;
import com.rookies5.myspringbootlab.entity.Book;
import com.rookies5.myspringbootlab.entity.BookDetail;
import com.rookies5.myspringbootlab.exception.BusinessException;
import com.rookies5.myspringbootlab.exception.ErrorCode;
import com.rookies5.myspringbootlab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    // 1. 도서 등록 (1:1 연관관계 포함)
    @Transactional
    public BookDTO.Response createBook(BookDTO.Request request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException(ErrorCode.ISBN_DUPLICATE, request.getIsbn());
        }

        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishDate(request.getPublishDate())
                .build();

        if (request.getDetailRequest() != null) {
            BookDetail detail = BookDetail.builder()
                    .description(request.getDetailRequest().getDescription())
                    .language(request.getDetailRequest().getLanguage())
                    .pageCount(request.getDetailRequest().getPageCount())
                    .publisher(request.getDetailRequest().getPublisher())
                    .coverImageUrl(request.getDetailRequest().getCoverImageUrl())
                    .edition(request.getDetailRequest().getEdition())
                    .build();
            book.setBookDetail(detail); // 양방향 연관관계 설정
        }

        return BookDTO.Response.fromEntity(bookRepository.save(book));
    }

    // 2. 전체 도서 조회
    public List<BookDTO.Response> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    // 3. ID로 도서 조회 (Fetch Join 활용)
    public BookDTO.Response getBookById(Long id) {
        Book book = bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "도서", "ID", id));
        return BookDTO.Response.fromEntity(book);
    }

    // 4. ISBN으로 도서 조회
    public BookDTO.Response getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbnWithBookDetail(isbn)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "도서", "ISBN", isbn));
        return BookDTO.Response.fromEntity(book);
    }

    // 5. 저자 이름으로 검색 (대소문자 무시, 포함 단어)
    public List<BookDTO.Response> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream()
                .map(BookDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    // 6. 제목으로 검색 (대소문자 무시, 포함 단어)
    public List<BookDTO.Response> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(BookDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    // 7. 전체 수정 (PUT)
    @Transactional
    public BookDTO.Response updateBook(Long id, BookDTO.Request request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "도서", "ID", id));

        // ISBN 중복 체크
        if (!book.getIsbn().equals(request.getIsbn()) && bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException(ErrorCode.ISBN_DUPLICATE, request.getIsbn());
        }

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPrice(request.getPrice());
        book.setPublishDate(request.getPublishDate());

        if (request.getDetailRequest() != null) {
            BookDetail detail = book.getBookDetail();
            if (detail == null) {
                detail = new BookDetail();
                book.setBookDetail(detail);
            }
            detail.setDescription(request.getDetailRequest().getDescription());
            detail.setLanguage(request.getDetailRequest().getLanguage());
            detail.setPageCount(request.getDetailRequest().getPageCount());
            detail.setPublisher(request.getDetailRequest().getPublisher());
            detail.setCoverImageUrl(request.getDetailRequest().getCoverImageUrl());
            detail.setEdition(request.getDetailRequest().getEdition());
        }
        return BookDTO.Response.fromEntity(book);
    }

    // 8. 부분 수정 (PATCH)
    @Transactional
    public BookDTO.Response patchBook(Long id, BookDTO.PatchRequest request) {
        Book book = bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "도서", "ID", id));

        if (request.getIsbn() != null) {
            if (!book.getIsbn().equals(request.getIsbn()) && bookRepository.existsByIsbn(request.getIsbn())) {
                throw new BusinessException(ErrorCode.ISBN_DUPLICATE, request.getIsbn());
            }
            book.setIsbn(request.getIsbn());
        }

        if (request.getTitle() != null) book.setTitle(request.getTitle());
        if (request.getAuthor() != null) book.setAuthor(request.getAuthor());
        if (request.getPrice() != null) book.setPrice(request.getPrice());
        if (request.getPublishDate() != null) book.setPublishDate(request.getPublishDate());

        if (request.getDetailRequest() != null) {
            BookDetail detail = book.getBookDetail();
            if (detail == null) {
                detail = new BookDetail();
                book.setBookDetail(detail);
            }
            if (request.getDetailRequest().getDescription() != null) detail.setDescription(request.getDetailRequest().getDescription());
            if (request.getDetailRequest().getLanguage() != null) detail.setLanguage(request.getDetailRequest().getLanguage());
            if (request.getDetailRequest().getPageCount() != null) detail.setPageCount(request.getDetailRequest().getPageCount());
            if (request.getDetailRequest().getPublisher() != null) detail.setPublisher(request.getDetailRequest().getPublisher());
            if (request.getDetailRequest().getCoverImageUrl() != null) detail.setCoverImageUrl(request.getDetailRequest().getCoverImageUrl());
            if (request.getDetailRequest().getEdition() != null) detail.setEdition(request.getDetailRequest().getEdition());
        }
        return BookDTO.Response.fromEntity(book);
    }

    // 9. 상세 정보만 부분 수정 (PATCH)
    @Transactional
    public void patchBookDetailOnly(Long id, BookDTO.BookDetailPatchRequest request) {
        Book book = bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "도서", "ID", id));

        BookDetail detail = book.getBookDetail();
        if (detail == null) throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "상세정보", "Book ID", id);

        if (request.getDescription() != null) detail.setDescription(request.getDescription());
        if (request.getLanguage() != null) detail.setLanguage(request.getLanguage());
        if (request.getPageCount() != null) detail.setPageCount(request.getPageCount());
        if (request.getPublisher() != null) detail.setPublisher(request.getPublisher());
        if (request.getCoverImageUrl() != null) detail.setCoverImageUrl(request.getCoverImageUrl());
        if (request.getEdition() != null) detail.setEdition(request.getEdition());
    }

    // 10. 도서 삭제
    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "도서", "ID", id));
        bookRepository.delete(book);
    }
}