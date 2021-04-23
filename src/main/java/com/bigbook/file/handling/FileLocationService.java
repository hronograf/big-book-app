package com.bigbook.file.handling;

import com.bigbook.book.BookEntity;
import com.bigbook.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor

@Service
public class FileLocationService {

    private final FileSystemRepository fileSystemRepository;
    private final BookRepository bookRepository;

    public void save(String isbn, byte[] bytes, String filename) throws Exception {
        String location = fileSystemRepository.save(bytes, filename);
        BookEntity bookEntity = bookRepository.findById(isbn).orElseThrow();
        bookEntity.setFilename(filename);
        bookEntity.setFileLocation(location);
        bookRepository.saveAndFlush(bookEntity);
    }

    public FileSystemResource find(String isbn) {
        BookEntity bookEntity = bookRepository.findById(isbn)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return fileSystemRepository.findInFileSystem(bookEntity.getFileLocation());
    }
}
