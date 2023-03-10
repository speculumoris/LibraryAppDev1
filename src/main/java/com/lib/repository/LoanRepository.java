package com.lib.repository;

import com.lib.domain.Book;
import com.lib.domain.Category;
import com.lib.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan,Long> {


    boolean existByBook(Book book);

    boolean existByCategory(Category category);



}
