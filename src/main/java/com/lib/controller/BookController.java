package com.lib.controller;

import com.lib.dto.BookDTO;
import com.lib.dto.response.LibResponse;
import com.lib.dto.response.ResponseMessage;
import com.lib.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {


    private final BookService bookService;


    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // SaveBook
    @PostMapping("/admin/{imageId}/add")
    //@PreAuthorize("hasRole('ADMIN')") EKLENCEK
    public ResponseEntity<LibResponse> saveBook(@PathVariable String imageId, @Valid @RequestBody BookDTO bookDTO){

        bookService.saveBook(imageId, bookDTO);

        LibResponse response = new LibResponse(ResponseMessage.BOOK_SAVED_RESPONSE_MESSAGE,true);

        return new ResponseEntity<>(response, HttpStatus.CREATED);


    }

    // getAllBook
    @GetMapping("/visitors/all")
    public ResponseEntity<List<BookDTO>> getAllBooks(){

        List<BookDTO> allBooks = bookService.getAllBooks();

        return ResponseEntity.ok(allBooks);

    }





}
