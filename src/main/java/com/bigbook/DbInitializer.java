package com.bigbook;

import com.bigbook.book.BookEntity;
import com.bigbook.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor

@Component
public class DbInitializer {
    private final BookRepository bookRepository;

    private final int INIT_BOOK_AMOUNT = 15;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for(int i = 0; i < INIT_BOOK_AMOUNT; i++) {
            bookRepository.saveAndFlush(
                    BookEntity.builder()
                            .isbn("0-00-" + i)
                            .title("initBook_" + i)
                            .author("initAuthor_" + i)
                            .build());
        }
    }
}
