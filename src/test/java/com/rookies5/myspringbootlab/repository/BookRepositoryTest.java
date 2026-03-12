package com.rookies5.myspringbootlab.repository;

import com.rookies5.myspringbootlab.entity.Book;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    // 1. 도서 등록
    @Test
    @Rollback(value = false)  // Rollback 처리를 하지 마세요
    //@Disabled //
    void testCreateBook() {
        // Given(준비단계)
        Book book = Book.builder()
                .title("스프링 부트 입문")
                .author("홍길동")
                .isbn("9788956746425")
                .price(30000)
                .publishDate(LocalDate.of(2025, 5, 7))
                .build();

        // When(실행단계)
        Book savedBook = bookRepository.save(book);

        // Then(검증단계)
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("스프링 부트 입문");
    }

    // 2. ISBN으로 도서 조회
    @Test
    void testFindByIsbn() {
        // When(실행단계)
        Optional<Book> optionalBook = bookRepository.findByIsbn("9788956746425");

        // Then(검증단계)
        if(optionalBook.isPresent()) {
            Book book = optionalBook.get();
            assertThat(book.getIsbn()).isEqualTo("9788956746425");
        } else {
            System.out.println("Book Not Found");
        }

        optionalBook.ifPresent(book -> System.out.println("찾은 책 제목: " + book.getTitle()));
    }

    // 3. 저자명으로 도서 목록 조회
    @Test
    void testFindByAuthor() {
        // When(실행단계)
        List<Book> books = bookRepository.findByAuthor("홍길동");

        // Then(검증단계)
        assertThat(books).isNotEmpty();
        System.out.println("홍길동의 책 개수: " + books.size());

        // 리스트 안에 있는 책들 이름 다 뽑아보기
        books.forEach(book -> System.out.println("저자 검색 결과: " + book.getTitle()));
    }

    // 4. 도서 정보 수정
    @Test
    @Rollback(value = false)
    void testUpdateBook() {
        // 조회를 하고 setter 호출하며 업데이트 됨 (더티 체킹)
        Book book = bookRepository.findByIsbn("9788956746425")
                .orElseThrow(() -> new RuntimeException("Book Not Found"));

        book.setTitle("스프링 부트 완벽 가이드 (수정판)");
        book.setPrice(35000);

        bookRepository.save(book); // 위에 Transactional 적혀있으면 자동으로 업데이트 되므로 안 적어도 됨

        assertThat(book.getTitle()).isEqualTo("스프링 부트 완벽 가이드 (수정판)");
    }

    // 5. 도서 삭제
    @Test
    @Rollback(value = false)
    @Disabled // 실수로 지워지는 거 방지하려면 달아두기
    void testDeleteBook() {
        // Given(준비단계)
        Book book = bookRepository.findByIsbn("9788956746425")
                .orElseThrow(() -> new RuntimeException("Book Not Found"));

        // When(실행단계)
        bookRepository.delete(book);

        // Then(검증단계)
        Optional<Book> deletedBook = bookRepository.findByIsbn("9788956746425");
        assertThat(deletedBook).isEmpty(); // 삭제됐으니까 텅 비어있어야 정상!
    }
}