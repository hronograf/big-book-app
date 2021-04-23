package com.bigbook.book.dto;

import com.bigbook.book.BookEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class SearchBookResponseDto {
    private List<BookEntity> books;
    private int canBeShown;
}
