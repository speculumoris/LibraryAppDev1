package com.lib.service;

import com.lib.domain.Book;
import com.lib.dto.BookDTO;
import com.lib.mapper.BookMapper;
import com.lib.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {


    private final BookRepository bookRepository;

    private final  ImageFileService imageFileService;

    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, ImageFileService imageFileService, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.imageFileService = imageFileService;
        this.bookMapper = bookMapper;
    }

    public void saveBook(String imageId, BookDTO bookDTO) {

        ImageFileService imageFile = imageFileService.findImageById(imageId);


    }

    public List<BookDTO> getAllBooks() {

        List<Book> bookList = bookRepository.findAll();

        return bookMapper.map(bookList);

    }
}
