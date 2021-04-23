package com.bigbook.book;

import com.bigbook.book.dto.CreateBookRequestDto;
import com.bigbook.book.dto.DeleteBookRequestDto;
import com.bigbook.book.dto.SearchBookResponseDto;
import com.bigbook.file.handling.FileLocationService;
import com.bigbook.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final FileLocationService fileLocationService;

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
            @RequestParam(name = "title", defaultValue = "") String title,
            @RequestParam(name = "isbn", defaultValue = "") String isbn,
            @RequestParam(name = "page", defaultValue = "0") int pageNumber
    ) {
        return Response.success(bookService.findBooks(title, isbn, pageNumber));
    }

    @PostMapping("/file/{isbn}")
    public void uploadFile(
            @PathVariable("isbn") String isbn,
            @RequestParam MultipartFile file
    ) throws Exception {
        fileLocationService.save(isbn, file.getBytes(), file.getOriginalFilename());
    }

    @GetMapping(value = "/file/{isbn}")
    FileSystemResource downloadFile(@PathVariable("isbn") String isbn) {
        return fileLocationService.find(isbn);
    }

}
