package com.lib.controller;

import com.lib.service.BookService;



public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


}
