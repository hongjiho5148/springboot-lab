package com.rookies5.myspringbootlab.controller;

import com.rookies5.myspringbootlab.dto.BookDTO;
import com.rookies5.myspringbootlab.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    // 1. 책 생성 (POST)
    @PostMapping
    public ResponseEntity<BookDTO.Response> createBook(@Valid @RequestBody BookDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(request));
    }

    // 2. 모든 책 조회 (GET)
    @GetMapping
    public ResponseEntity<List<BookDTO.Response>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // 3. ID로 책 조회 (GET)
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO.Response> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    // 4. ISBN으로 책 조회 (GET)
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO.Response> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    // 5. 저자로 책 검색 (GET)
    @GetMapping("/search/author")
    public ResponseEntity<List<BookDTO.Response>> searchByAuthor(@RequestParam String author) {
        return ResponseEntity.ok(bookService.searchByAuthor(author));
    }

    // 6. 제목으로 책 검색 (GET)
    @GetMapping("/search/title")
    public ResponseEntity<List<BookDTO.Response>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(bookService.searchByTitle(title));
    }

    // 7. 책 전체 수정 (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO.Response> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookDTO.Request request) {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    // 8. 책 삭제 (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    // 9. 책 전체 정보 중 일부만 수정 (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<BookDTO.Response> patchBook(
            @PathVariable Long id,
            @Valid @RequestBody BookDTO.PatchRequest request) {
        return ResponseEntity.ok(bookService.patchBook(id, request));
    }

    // 10. 책 상세 정보(Detail)만 수정 (PATCH)
    @PatchMapping("/{id}/detail")
    public ResponseEntity<Void> patchBookDetail(
            @PathVariable Long id,
            @Valid @RequestBody BookDTO.BookDetailPatchRequest request) {
        bookService.patchBookDetailOnly(id, request);
        return ResponseEntity.ok().build();
    }
}