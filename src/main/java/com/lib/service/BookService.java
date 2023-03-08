package com.lib.service;

import com.lib.domain.Book;
import com.lib.domain.ImageFile;
import com.lib.dto.BookDTO;
import com.lib.exception.ConflictException;
import com.lib.exception.message.ErrorMessage;
import com.lib.mapper.BookMapper;
import com.lib.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        ImageFile imageFile = imageFileService.findImageById(imageId);

        Integer usedBookCount = bookRepository.findBookCountByImageId(imageFile.getId());
        if (usedBookCount > 0) {
            throw new ConflictException(ErrorMessage.IMAGE_USED_MESSAGE);
        }

        Book book = bookMapper.bookDTOToBook(bookDTO);

        Set<ImageFile> imFiles = new HashSet<>();
        imFiles.add(imageFile);
//        book.setImageFile(imFiles);

        bookRepository.save(book);

    }

    public List<BookDTO> getAllBooks() {

        List<Book> bookList = bookRepository.findAll();

        return bookMapper.map(bookList);

    }
}
