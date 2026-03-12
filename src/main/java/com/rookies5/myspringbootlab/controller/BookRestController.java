package com.rookies5.myspringbootlab.controller;

import com.rookies5.myspringbootlab.entity.Book;
import com.rookies5.myspringbootlab.repository.BookRepository;
// 본인 프로젝트 경로에 맞게 BusinessException을 임포트해주세요!
import com.rookies5.myspringbootlab.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    @Autowired
    private BookRepository bookRepository;

    // 1. 새 도서 등록
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    // 2. 모든 도서 조회
    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // 3. ID로 특정 도서 조회 (map / orElse 사용)
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(book -> ResponseEntity.ok(book))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 4. ISBN으로 도서 조회 (BusinessException 사용)
    @GetMapping("/isbn/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("해당 ISBN의 도서를 찾을 수 없습니다."));
    }

    // 5. 도서 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Book book = getExistBook(id);

        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setPrice(bookDetails.getPrice());
        book.setPublishDate(bookDetails.getPublishDate());

        Book updatedBook = bookRepository.save(book);
        return ResponseEntity.ok(updatedBook);
    }

    //도서 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Book book = getExistBook(id); // 단 한 줄로 끝!

        bookRepository.delete(book);
        return ResponseEntity.ok().build();
    }

    // 클래스 맨 아래에 추가할 도우미 메서드
    private Book getExistBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("해당 도서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }
}