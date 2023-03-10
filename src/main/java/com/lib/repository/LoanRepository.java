package com.lib.repository;

import com.lib.domain.Loan;
import com.lib.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan,Long> {

    @EntityGraph(attributePaths = {"book","book.image"})
    List<Loan> findAll();

    @EntityGraph(attributePaths = {"book","book.image"})
    Page<Loan> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"car", "car.image","user"})
    Optional<Loan> findById(Long id);

    @EntityGraph(attributePaths = {"book", "book.image","user"})
    Optional<Loan> findUserById(Long id, User user);
}
