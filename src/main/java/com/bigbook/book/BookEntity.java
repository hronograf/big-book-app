package com.bigbook.book;


import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "book")
public class BookEntity {

    @Id
    @Column(name = "isbn")
    private String isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "filename")
    private String filename;

    @Column(name = "file_loacation")
    private String fileLocation;

    public BookEntity(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public static Specification<BookEntity> titleIgnoreCaseContains(String title) {
        String lowercaseTitle = title.toLowerCase();
        return (book, cq, cb) -> cb.like(cb.lower(book.get("title")), "%" + lowercaseTitle + "%");
    }

    public static Specification<BookEntity> isbnContains(String isbn) {
        return (book, cq, cb) -> cb.like(book.get("isbn"), "%" + isbn + "%");
    }

    public static int BOOKS_PER_PAGE = 10;

}
