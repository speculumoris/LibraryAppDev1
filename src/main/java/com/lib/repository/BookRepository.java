package com.lib.repository;

import com.lib.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    @Query("SELECT count(*) from Book b join b.image img where img.id=:id")
    Integer findBookCountByImageId(String id);

    @EntityGraph(attributePaths = {"image"})//!!! EAGER yaptik
    List<Book> findAll();

    @EntityGraph(attributePaths = {"image"})//!!! EAGER yaptik
    Page<Book> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"image"})//!!! EAGER yaptik
    Optional<Book> findBookById(Long id);

    @Query("Select b from Book b join b.image im where im.id=:id")
    List<Book> findBooksByImageId(@Param("id") String id);

    @EntityGraph(attributePaths = "id")
    List<Book> getAllBy();


}
