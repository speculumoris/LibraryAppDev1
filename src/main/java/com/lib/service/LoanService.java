package com.lib.service;

import com.lib.domain.Book;
import com.lib.domain.Category;
import com.lib.domain.Loan;
import com.lib.domain.User;
import com.lib.dto.LoanDTO;
import com.lib.dto.request.LoanRequest;
import com.lib.dto.response.LibResponse;
import com.lib.exception.BadRequestException;
import com.lib.exception.ResourceNotFoundException;
import com.lib.exception.message.ErrorMessage;
import com.lib.mapper.LoanMapper;
import com.lib.repository.LoanRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    private final LoanMapper loanMapper;

    public LoanService(LoanRepository loanRepository, LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
    }

    public Page<LoanDTO> getAllLoanWithPage(Pageable pageable) {
        Page<Loan> getAllLoan = loanRepository.findAll(pageable);

        return getAllLoan.map(map -> loanMapper.loanToLoanDTO(map));
    }


    public void createLoan(LoanRequest loanRequest, User user, Book book) {
        LocalDateTime returnDate = loanRequest.getReturnDate();
        LocalDateTime expireDate = loanRequest.getExpireDate();
        int score = user.getScore();

        checkBookIsAvailableAndLoanable(book);
        checkUserScoreForLoan(user, loanRequest.getLoanDate(), loanRequest.getExpireDate());

        Loan loan = loanMapper.loanRequestToLoan(loanRequest);

        if (returnDate.equals(expireDate) || returnDate.isBefore(expireDate)) {
            if (!(score > 2) && !(score < -2)) {
                user.setScore(score + 1);
            } else user.setScore(score - 1);

        }

        loan.setBook(book);
        loan.setUser(user);

        loanRepository.save(loan);
    }

    //Kitap müsait mi?
    //Kitap kiralanabilir mi?
    //Kaç kitap alınacak?
    //Kitap almak için puan yeterli mi?
    private boolean checkBookIsAvailableAndLoanable(Book book) {
        if (!book.isActive()) {
            throw new BadRequestException(ErrorMessage.BOOK_IS_NOT_AVAILABLE_MESSAGE);
        }
        if (!book.isLoanable()) {
            throw new BadRequestException(ErrorMessage.BOOK_IS_NOT_LOANABLE_MESSAGE);
        }
        return true;
    }

    private LocalDateTime checkUserScoreForLoan(User user, LocalDateTime loanDate,
                                                LocalDateTime finishDate) {

        switch (user.getScore()) {
            case -2 -> finishDate = loanDate.plusDays(3);
            case -1 -> finishDate = loanDate.plusDays(6);
            case 0 -> finishDate = loanDate.plusDays(10);
            case 1 -> finishDate = loanDate.plusDays(15);
            case 2 -> finishDate = loanDate.plusDays(20);
            default -> throw new BadRequestException(ErrorMessage.SCORE_IS_NOT_ENOUGH);
        }
        return finishDate;
    }


    public LoanDTO getLoanDeatilsById(Long id) {

        Loan loan=getById(id);
        return loanMapper.loanToLoanDTO(loan);

    }

    private Loan getById(Long id) {

       Loan loan= loanRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.LOAN_NOT_FOUND_MESSAGE,id)));
       return loan;

    }

    public List<LoanDTO> getAllLoan() {

       List<Loan>loanList= loanRepository.findAll();
       return loanMapper.loanListToLoanDTOList(loanList);
    }

    public LoanDTO findByIdAndUser(Long id, User user) {
       Loan loan= loanRepository.findUserById(id,user).orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id)));
       return loanMapper.loanToLoanDTO(loan);
    }

    public boolean existByCategory(Category category) {

        return loanRepository.existsByCategory(category);

    }

    public boolean existByBook(Book book) {

        return loanRepository.existsByBook(book);

    }
}
