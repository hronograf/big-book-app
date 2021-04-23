package com.bigbook;

import com.bigbook.book.BookEntity;
import com.bigbook.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor

@Controller
public class PageController {

    private final BookRepository bookRepository;

    @GetMapping({"", "/"})
    public String index(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "index";
    }

    @GetMapping("/book/{isbn}")
    public String getBookInfo(@PathVariable("isbn") String isbn, Model model) {
        BookEntity bookEntity = bookRepository.findById(isbn).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("book", bookEntity);
        return "bookPage";
    }
}
