package com.rookies5.myspringbootlab.repository;

import com.rookies5.myspringbootlab.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    // ISBN으로 도서 조회 (단건)
    Optional<Book> findByIsbn(String isbn);

    // 저자명으로 도서 목록 조회 (다건)
    List<Book> findByAuthor(String author);
}