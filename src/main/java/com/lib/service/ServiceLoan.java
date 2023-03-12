package com.lib.service;


import com.lib.domain.Book;
import com.lib.domain.Loan;
import com.lib.domain.User;
import com.lib.dto.LoanDTO;
import com.lib.dto.request.LoanRequest;
import com.lib.exception.BadRequestException;
import com.lib.exception.ResourceNotFoundException;
import com.lib.repository.BookRepository;
import com.lib.repository.LoanRepository;
import com.lib.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.List;

import static com.lib.exception.message.ErrorMessage.*;

@Service
class ServiceLoan {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public ServiceLoan(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public LoanRequest createLoan(LoanDTO loanDTO) {
        User user = userRepository.findById(loanDTO.getUserId()).orElseThrow(() ->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, loanDTO.getUserId())));//GetByIDUser
        Book book = bookRepository.findById(loanDTO.getBookId()).orElseThrow(()
                -> new ResourceNotFoundException(String.format(BOOK_NOT_FOUND_MESSAGE, loanDTO.getBookId())));//getByIdBook
        LocalDateTime current = LocalDateTime.now();//Now
        List<Loan> expiredLoans = loanRepository.findExpiredLoansBy(loanDTO.getUserId(), current);
        List<Loan> activeLoansOfUser = loanRepository.findLoansByUserIdAndExpireDateIsNull(user);

        if (!book.isLoanable()) {
            throw new BadRequestException("book is not loanable");
        }
        if (expiredLoans.size() < 0){
            throw new IllegalStateException("You do not have a permission to loan");
        }

        Loan loan = new Loan();
        switch (user.getScore()) {
            case 2:
                loan.setLoanDate(current);
                loan.setExpireDate(current.plusDays(20));
                break;
            case 1:
                if (activeLoansOfUser.size() < 4) {
                    loan.setLoanDate(current);
                    loan.setExpireDate(current.plusDays(15));
                }
                break;
            case 0:
                if (activeLoansOfUser.size() < 3) {
                    loan.setLoanDate(current);
                    loan.setExpireDate(current.plusDays(10));
                }
                break;
            case -1:
                if (activeLoansOfUser.size() < 2) {
                    loan.setLoanDate(current);
                    loan.setExpireDate(current.plusDays(6));
                }
                break;
            case -2:
                if (activeLoansOfUser.size() < 1) {
                    loan.setLoanDate(current);
                    loan.setExpireDate(current.plusDays(3));
                }
                break;
            default:
                throw new BadRequestException("The user score is not between -2 and +2, " + "(from createLoan Method in the LoanService)");
        }
        loan.setBookId(book);
        loan.setUserId(user);
        loan.setNotes(loanDTO.getNotes());
        loanRepository.save(loan);
        book.setLoanable(false);
        bookRepository.save(book);
        LoanResponse loanResponse = new LoanResponse();
        loanResponse.setId(loan.getId());
        loanResponse.setUserId(loan.getUserId().getId());
        loanResponse.setBookId(loan.getBookId());
        return loanResponse;
    }

    //3. method
    public List<LoanResponseBookUser> findAllLoansByUserId(Long userId, Pageable pageable) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, userId)));
        return loanRepository.findAllLoanByUser(userId, pageable);
    }

    //4. method
    public List<Loan> getLoanedBookByBookId(Long bookId, Pageable pageable) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(BOOK_NOT_FOUND_MESSAGE, bookId)));
        return loanRepository.findAllByBookId(book, pageable);
    }

    //5.method
    public Loan getLoanById(Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() ->
                new BadRequestException(String.format(LOAN_NOT_FOUND_MSG, loanId)));
        return loan;        // findById methodu JPA'nın kendi methodu olduğu için
        // OOptional<Loan> dönüyor içinden Loan objesini çekmek        // için get() kullandım.    }
        public LoanUpdateResponse updateLoan (Long loanId, UpdateLoanDTO updateLoanDTO)throws BadRequestException {
            Loan loan = loanRepository.findById(loanId).orElseThrow(()
                    -> new ResourceNotFoundException(String.format(LOAN_NOT_FOUND_MSG, loanId)));
            User user = loan.getUserId();
            Book book = loan.getBookId();
            try {
                if (updateLoanDTO.getReturnDate() != null) {
                    book.setLoanable(true);
                    loan.setReturnDate(updateLoanDTO.getReturnDate());
                    bookRepository.save(book);
                    loanRepository.save(loan);
                    if (updateLoanDTO.getReturnDate().isEqual(loan.getReturnDate()) || updateLoanDTO.getReturnDate().isBefore(loan.getReturnDate())) {
                        user.setScore(user.getScore() + 1);
                        userRepository.save(user);
                        return new LoanUpdateResponse(loan);
                    } else {
                        user.setScore(user.getScore() - 1);
                        userRepository.save(user);
                        return new LoanUpdateResponse(loan);
                    }
                } else {
                    loan.setExpireDate(updateLoanDTO.getExpireDate());
                    loan.setNotes(updateLoanDTO.getNotes());
                    bookRepository.save(book);
                    userRepository.save(user);
                    loanRepository.save(loan);
                    return new LoanUpdateResponse(loan);
                }
            } catch (RuntimeException e) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            } finally {
                return new LoanUpdateResponse(loan);
            }
            return new LoanUpdateResponse(loan);
        }
        //1.method
        public List<Loan> findLoansWithPageByUserId(Long userId, Pageable pageable){
            return loanRepository.findAllWithPageByUserId(userId, pageable);
        }
        //2.method
        public Loan getByIdAndUserId (Long loanId, Long userId){
            Loan loan = loanRepository.findByIdAndUserId(loanId, userId);
            return loan;
        }
    }
}
