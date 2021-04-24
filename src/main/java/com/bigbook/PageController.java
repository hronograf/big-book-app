package com.bigbook;

import com.bigbook.book.BookEntity;
import com.bigbook.book.BookRepository;
import com.bigbook.exceptions.WebException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor

@Controller
public class PageController {

    private final BookRepository bookRepository;

    @GetMapping({"", "/"})
    public String index(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "index";
    }

    @GetMapping("/book")
    public String getBookInfo(@RequestParam("isbn") String isbn, Model model) {
        BookEntity bookEntity = bookRepository.findById(isbn).orElseThrow(() -> new WebException(HttpStatus.NOT_FOUND, "Book not found"));
        model.addAttribute("book", bookEntity);
        return "bookPage";
    }
}
