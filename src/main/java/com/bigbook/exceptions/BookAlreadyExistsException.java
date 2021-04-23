package com.bigbook.exceptions;

import org.springframework.http.HttpStatus;

public class BookAlreadyExistsException extends WebException {
    public BookAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "Book with this isbn has already been added");
    }
}
