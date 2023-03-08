package com.lib.controller;

import com.lib.dto.BookDTO;
import com.lib.dto.response.LibResponse;
import com.lib.dto.response.ResponseMessage;
import com.lib.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping("/admin/{imageId}/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> saveBook(@PathVariable String imageId, @Valid @RequestBody BookDTO bookDTO){

        bookService.saveBook(imageId, bookDTO);

        LibResponse response = new LibResponse(ResponseMessage.BOOK_SAVED_RESPONSE_MESSAGE,true);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }


    @GetMapping("/visitors/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER') or hasRole('AUTHOR') or hasRole('LOAN') or hasRole('PUBLİSHER')")
    public ResponseEntity<List<BookDTO>> getAllBooks(){

        List<BookDTO> allBooks = bookService.getAllBooks();

        return ResponseEntity.ok(allBooks);

    }


    @GetMapping("/visitors/pages")
    public ResponseEntity<Page<BookDTO>> getAllBooksWithPage(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String prop,
            @RequestParam(value = "direction", required = false, defaultValue = "DESC")Sort.Direction direction){

        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));

        Page<BookDTO> pageDTO =  bookService.findAllWithPage(pageable);

        return ResponseEntity.ok(pageDTO);

    }

    @GetMapping("/visitors/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER') or hasRole('AUTHOR') or hasRole('LOAN') or hasRole('PUBLİSHER')")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id){

        BookDTO bookDTO = bookService.findById(id);

        return ResponseEntity.ok(bookDTO);

    }

    @PutMapping("/admin/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> updateBook(@RequestParam("id") Long id,
                                                  @RequestParam("imageId") String imageId,
                                                  @Valid @RequestBody BookDTO bookDTO){


        bookService.updateBook(id, imageId, bookDTO);

        LibResponse response = new LibResponse(ResponseMessage.BOOK_UPDATED_RESPONSE_MESSAGE,true);

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/admin/{id}/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> deleteBook(@PathVariable Long id){

        bookService.removeById(id);

        LibResponse response = new LibResponse(ResponseMessage.BOOK_DELETED_RESPONSE_MESSAGE,true);

        return ResponseEntity.ok(response);


    }


}
