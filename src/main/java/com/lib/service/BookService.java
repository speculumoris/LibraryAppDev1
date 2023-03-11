package com.lib.service;

import com.lib.domain.Book;
import com.lib.domain.Category;
import com.lib.domain.ImageFile;
import com.lib.dto.BookDTO;
import com.lib.exception.BadRequestException;
import com.lib.exception.ConflictException;
import com.lib.exception.ResourceNotFoundException;
import com.lib.exception.message.ErrorMessage;
import com.lib.mapper.BookMapper;
import com.lib.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookService {

    private final BookRepository bookRepository;

    private final ImageFileService imageFileService;

    private final BookMapper bookMapper;

    private final LoanService loanService;


    public BookService(BookRepository bookRepository, ImageFileService imageFileService, BookMapper bookMapper, LoanService loanService) {
        this.bookRepository = bookRepository;
        this.imageFileService = imageFileService;
        this.bookMapper = bookMapper;
        this.loanService = loanService;
    }

    public Book getBookById(Long bookId){

        Book book=bookRepository.findById(bookId).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_EXCEPTION,bookId)));
        return book;
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
        book.setImage(imFiles);

        bookRepository.save(book);

    }

    public List<BookDTO> getAllBooks() {

        List<Book> bookList = bookRepository.findAll();

        return bookMapper.map(bookList);

    }

    public Page<BookDTO> findAllWithPage(Pageable pageable) {


        Page<Book> bookPage = bookRepository.findAll(pageable);

        return bookPage.map(book -> bookMapper.bookToBookDTO(book));

    }

    public BookDTO findById(Long id) {

        Book book = getBook(id);

        return bookMapper.bookToBookDTO(book);

    }

    private Book getBook(Long id){

        Book book = bookRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id)));

        return book;


    }

    public void updateBook(Long id, String imageId, BookDTO bookDTO) {

        Book book = getBook(id);

        if(book.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        ImageFile imageFile =  imageFileService.findImageById(imageId);

        List<Book> bookList = bookRepository.findBooksByImageId(imageFile.getId());
        for (Book b : bookList) {
            if(book.getId().longValue()!= b.getId().longValue()){
                throw  new ConflictException(ErrorMessage.IMAGE_USED_MESSAGE);
            }
        }

        book.setId(bookDTO.getId());
        book.setName(bookDTO.getName());
        book.setIsbn(bookDTO.getIsbn());
        book.setPageCount(bookDTO.getPageCount());
        book.setPublishDate(bookDTO.getPublishDate());
        book.setShelfCode(bookDTO.getShelfCode());

        book.getImage().add(imageFile);

        bookRepository.save(book);


    }


    public void removeById(Long id) {

        Book book = getBook(id);

        if(book.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        boolean exist =  loanService.existByBook(book);
        if(exist) {
            throw  new BadRequestException(ErrorMessage.BOOK_USED_BY_LOAN_MESSAGE);
        }

        bookRepository.delete(book);

    }


    public Book findBookById(Long bookId) {

        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION,bookId))
        );

        return book;

    }

















}
