package com.lib.repository;


import com.lib.domain.Book;
import com.lib.domain.Category;
import com.lib.domain.Loan;
import com.lib.domain.User;
import com.lib.dto.LoanDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {


    @EntityGraph(attributePaths = {"book", "book.image"})
    List<Loan> findAll();

    @EntityGraph(attributePaths = {"book", "book.image"})
    Page<Loan> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"book", "book.image", "user"})
    Optional<Loan> findById(Long id);

    @EntityGraph(attributePaths = {"book", "book.image", "user"})
    Optional<Loan> findUserById(Long id, User user);

    boolean existsByCategory(Category category);

    boolean existsByBook(Book book);

    @Query("Select l from Loan l where l.user.id=:id and l.expireDate=:null")
    List<Loan>findLoansByUserIdAndExpireDateIsNull(@Param("id") User userId);

    @EntityGraph(attributePaths = {"book", "book.image", "user"})
    Page<Loan> findAllByUser(User user, Pageable pageable);

    @EntityGraph(attributePaths = {"book", "book.image"})
    Page<Loan> findAllByBook(Book book, Pageable pageable);

}

