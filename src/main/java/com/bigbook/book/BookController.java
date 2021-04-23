package com.bigbook.book;

import com.bigbook.book.dto.CreateBookRequestDto;
import com.bigbook.book.dto.DeleteBookRequestDto;
import com.bigbook.book.dto.SearchBookResponseDto;
import com.bigbook.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @PostMapping("/create")
    public Response<String> createBookPost(@RequestBody CreateBookRequestDto requestDto) {
        bookService.saveBook(requestDto);
        return Response.of("Successfully added", null);
    }

    @PostMapping("/delete")
    public void deleteBook(@RequestBody DeleteBookRequestDto requestDto) {
        bookService.deleteBook(requestDto);
    }

    @GetMapping("/search")
    public Response<SearchBookResponseDto> getFilteredByTitleBooks(
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "isbn", defaultValue = "") String isbn,
            @RequestParam(value = "page", defaultValue = "0") int pageNumber
    ) {
        return Response.success(bookService.findBooks(title, isbn, pageNumber));
    }

}
