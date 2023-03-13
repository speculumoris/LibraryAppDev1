package com.lib.controller;

import com.lib.domain.Book;
import com.lib.domain.User;
import com.lib.dto.LoanDTO;
import com.lib.dto.request.LoanRequest;
import com.lib.dto.request.LoanUpdateRequest;
import com.lib.dto.response.LibResponse;
import com.lib.dto.response.ResponseMessage;
import com.lib.service.BookService;
import com.lib.service.LoanService;
import com.lib.service.UserService;
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
@RequestMapping("/loan")
public class LoanController {

    private final LoanService loanService;

    private final UserService userService;

    private final BookService bookService;

    public LoanController(LoanService loanService, UserService userService, BookService bookService) {
        this.loanService = loanService;
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping//http://localhost:8080/loan page
    public ResponseEntity<Page<LoanDTO>> getAllLoanWithPage( @RequestParam("page") int page,
                                                             @RequestParam("size") int size,
                                                             @RequestParam("sort") String prop,//neye göre sıralanacağı belirtiliyor
                                                             @RequestParam(value = "direction",
                                                                     required = false, // direction required olmasın
                                                                     defaultValue = "DESC") Sort.Direction direction){
        Pageable pageable= PageRequest.of(page,size,Sort.by(direction,prop));
        Page<LoanDTO> pageLoanDTO=loanService.getAllLoanWithPage(pageable);

        return ResponseEntity.ok(pageLoanDTO);
    }

    @PostMapping//CreateOk
    public ResponseEntity<LibResponse> createLoan(@RequestParam Long bookId,
                                                  @Valid @RequestBody LoanRequest loanRequest){

        Book book=bookService.getBookById(bookId);
        User user=userService.getCurrentUser();

        loanService.createLoan(loanRequest,user,book);

        LibResponse libResponse=new LibResponse(ResponseMessage.LOAN_CREATED_RESPONSE_MESSAGE,true);

        return new ResponseEntity<>(libResponse, HttpStatus.CREATED);



    }

    @PostMapping("/{id}")//2.ok
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable Long id){

        LoanDTO loan=loanService.getLoanDeatilsById(id);

        return ResponseEntity.ok(loan);
    }

    @PostMapping
    public ResponseEntity<List<LoanDTO>> getAllLoanDTO(){

       List<LoanDTO> loanDTOList= loanService.getAllLoan();
       return ResponseEntity.ok(loanDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> loanDTOSAuthUser(@PathVariable Long id){

        User user=userService.getCurrentUser();
      LoanDTO loanDTO=  loanService.findByIdAndUser(id,user);

      return ResponseEntity.ok(loanDTO);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Page<LoanDTO>> getLoanByUserId(@RequestParam("userId") Long userId,
                                                         @RequestParam("page") int page,
                                                         @RequestParam("size") int size,
                                                         @RequestParam("sort") String prop,//neye göre sıralanacağı belirtiliyor
                                                         @RequestParam(value = "direction",
                                                                 required = false, // direction required olmasın
                                                                 defaultValue = "DESC") Sort.Direction direction) {
        Pageable pageable=PageRequest.of(page, size,Sort.by(direction,prop));
        User user = userService.getById(userId);
        Page<LoanDTO> loanDTOS=loanService.getLoansByUserId(user,pageable);

        return ResponseEntity.ok(loanDTOS);

    }

    @PutMapping
    public ResponseEntity<LibResponse> updateLoan(@RequestParam("loanId") Long loanId,
                                                  @RequestParam("bookId") Long bookId,
                                                  @RequestParam LoanUpdateRequest loanUpdateRequest){
        Book book=bookService.getBookById(bookId);
        User user =userService.getCurrentUser();
        loanService.updateLoan(loanId,book,loanUpdateRequest,user);

        LibResponse libResponse=new LibResponse(ResponseMessage.LOAN_UPDATE_RESPONSE_MESSAGE,true);

        return ResponseEntity.ok(libResponse);


    }
    @GetMapping("/loan/{id}")//loans/loan/id=3
    public ResponseEntity<LoanDTO> loanWıthId(@RequestParam("id")Long id){
        LoanDTO loanDTO=loanService.getLoanDeatilsById(id);
        return ResponseEntity.ok(loanDTO);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<Page<LoanDTO>> loanWıthId(@PathVariable Long bookId,
                                                    @RequestParam ("page") int page,
                                                    @RequestParam("size") int size,
                                                    @RequestParam("sort") String prop,
                                                    @RequestParam("direction") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<LoanDTO> loanedBook=loanService.getLoanedBooks(bookId,pageable);

       return ResponseEntity.ok(loanedBook);
    }

}
