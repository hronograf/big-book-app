package com.bigbook.book;

import com.bigbook.book.dto.CreateBookRequestDto;
import com.bigbook.book.dto.DeleteBookRequestDto;
import com.bigbook.book.dto.SearchBookResponseDto;
import com.bigbook.exceptions.BookAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class BookService {

    private final BookRepository bookRepository;

    public SearchBookResponseDto findBooks(String title, String isbn, int pageNumber) {
        Page<BookEntity> page = bookRepository.findAll(
                Specification.where(BookEntity.titleIgnoreCaseContains(title)).and(BookEntity.isbnContains(isbn)),
                PageRequest.of(pageNumber, BookEntity.BOOKS_PER_PAGE, Sort.by("title").and(Sort.by("isbn")))
        );
        int canBeShown = (int) page.getTotalElements() - (pageNumber + 1) * BookEntity.BOOKS_PER_PAGE;
        if (canBeShown < 0)
            canBeShown = 0;
        return new SearchBookResponseDto(page.getContent(), canBeShown);
    }

    public void saveBook(CreateBookRequestDto requestDto) {
        if (bookRepository.existsById(requestDto.getIsbn()))
            throw new BookAlreadyExistsException();
        bookRepository.saveAndFlush(new BookEntity(requestDto.getIsbn(), requestDto.getTitle(), requestDto.getAuthor()));
    }

    public void deleteBook(DeleteBookRequestDto requestDto) {
        bookRepository.deleteById(requestDto.getIsbn());
    }
}
