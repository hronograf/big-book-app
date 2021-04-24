package com.bigbook.file.handling;

import com.bigbook.book.BookEntity;
import com.bigbook.book.BookRepository;
import com.bigbook.exceptions.WebException;
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
        System.out.println("in save");
        BookEntity bookEntity = bookRepository.findById(isbn).orElseThrow();
        fileSystemRepository.delete(bookEntity.getFileLocation());

        String fileLocation = fileSystemRepository.save(bytes, filename);
        bookEntity.setFilename(filename);
        bookEntity.setFileLocation(fileLocation);
        bookRepository.saveAndFlush(bookEntity);
        System.out.println("saved");
    }

    public FileSystemResource find(String isbn) {
        BookEntity bookEntity = bookRepository.findById(isbn)
                .orElseThrow(() -> new WebException(HttpStatus.NOT_FOUND, "Book not found"));
        if (bookEntity.getFileLocation() == null) throw new WebException(HttpStatus.NOT_FOUND, "File not found");

        return fileSystemRepository.findInFileSystem(bookEntity.getFileLocation());
    }
}
