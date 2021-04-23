package com.bigbook.book.dto;

import lombok.Data;

@Data
public class CreateBookRequestDto {
    private String isbn;
    private String title;
    private String author;
}
