package com.rookies5.myspringbootlab.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_details")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDetail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String description;

    @Column(length = 50)
    private String language;

    private Integer pageCount;

    @Column(length = 100)
    private String publisher;

    @Column(length = 500)
    private String coverImageUrl;

    @Column(length = 50)
    private String edition;

    // 외래 키를 가지는 연관관계의 주인
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", unique = true, nullable = false)
    private Book book;
}